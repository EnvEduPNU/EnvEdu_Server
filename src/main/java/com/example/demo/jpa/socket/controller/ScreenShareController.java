package com.example.demo.jpa.socket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ScreenShareController {

//    private final SimpMessagingTemplate messagingTemplate;

//    @Async
//    @MessageMapping("/sendOffer/{sessionId}")
//    public void sendOffer(@DestinationVariable String sessionId, @Payload String offer) {
//            messagingTemplate.convertAndSend(String.format("/topic/offer/%s", sessionId), offer);
//            log.info("Offer sent for session {}: {}", sessionId, offer);
//    }
//
//    @Async
//    @MessageMapping("/sendAnswer/{sessionId}")
//    public void sendAnswer(@DestinationVariable String sessionId, @Payload String answer) {
//            messagingTemplate.convertAndSend(String.format("/topic/answer/%s", sessionId), answer);
//            log.info("Answer sent for session {}: {}", sessionId, answer);
//    }
//
//    @Async
//    @MessageMapping("/sendCandidate/{sessionId}")
//    public void sendCandidate(@DestinationVariable String sessionId, @Payload String candidate) {
//            messagingTemplate.convertAndSend(String.format("/topic/candidate/%s", sessionId), candidate);
//            log.info("Candidate sent for session {}: {}", sessionId, candidate);
//    }


    @MessageMapping("/sendOffer")
    @SendTo("/topic/screen-share")
    public String sendOffer(@Payload String offer) {
        return offer;
    }

    @MessageMapping("/sendAnswer")
    @SendTo("/topic/screen-share")
    public String sendAnswer(@Payload String answer) {
        return answer;
    }

    @MessageMapping("/sendCandidate")
    @SendTo("/topic/screen-share")
    public String sendCandidate(@Payload String candidate) {
        return candidate;
    }
}
