package com.example.demo.jpa.socket.config;

import com.example.demo.jpa.device.repository.UserDeviceRepository;
import com.example.demo.jpa.device.service.UserDeviceService;
import com.example.demo.jpa.socket.interceptor.DeviceSocketInterceptor;
import com.example.demo.jpa.socket.interceptor.SocketConnectionInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
    public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final UserDeviceRepository userDeviceRepository;
    private final UserDeviceService userDeviceService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
    // Stomp 사용해서 아두이노 기기와 연결해주는 메서드, 10초마다 연결의 가용성을 확인한다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //아두이노 기기와 연결되는 endpoint
        registry.addEndpoint("/device")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new DeviceSocketInterceptor(userDeviceService))
                .withSockJS()
                .setHeartbeatTime(5000L);

        registry.addEndpoint("/client/socket")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new SocketConnectionInterceptor(userDeviceRepository))
                .withSockJS()
                .setDisconnectDelay(5000L);

        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();

        registry.addEndpoint("/screen-share").setAllowedOriginPatterns("*").withSockJS();


    }

}