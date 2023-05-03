package com.example.demo.seed.socket.config;

import com.example.demo.device.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class DeviceSocketInterceptor implements HandshakeInterceptor {
    private final UserDeviceRepository userDeviceRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String MAC = Objects.requireNonNull(request.getHeaders().get("mac")).toString();
            boolean exists =  userDeviceRepository.existsByMac(MAC);
            if(exists) {
                log.info("connected from " + MAC);
                return true;
            }
            log.warn("device connection attempt from disallowed address " + MAC);
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
