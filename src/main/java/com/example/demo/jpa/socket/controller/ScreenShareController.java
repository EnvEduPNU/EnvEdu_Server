package com.example.demo.jpa.socket.controller;

import com.example.demo.jpa.socket.dto.ScreenShareDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ScreenShareController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendOffer")
    public void sendOffer(@Payload String offer) {
        messagingTemplate.convertAndSend("/topic/offer", offer);
    }

    @MessageMapping("/sendAnswer")
    public void sendAnswer(@Payload String answer) {
        messagingTemplate.convertAndSend("/topic/answer", answer);
    }

    @MessageMapping("/sendCandidate")
    public void sendCandidate(@Payload String candidate) {
        messagingTemplate.convertAndSend("/topic/candidate", candidate);
    }


//    @MessageMapping("/screen-share/{sessionId}")
//    public void handleSignal(@DestinationVariable String sessionId, ScreenShareDTO message) {
//        log.info("잘 들어가고 있나?? : " + sessionId);
//        messagingTemplate.convertAndSend("/topic/" + sessionId, message);
//    }
}
