package com.example.demo.jpa.socket.controller;

import com.example.demo.jpa.socket.config.ThreadConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.scheduling.annotation.Async;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ScreenShareController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ThreadConfig taskExecutor;

    @Async
    @MessageMapping("/sendOffer/{sessionId}")
    public void sendOffer(@DestinationVariable String sessionId, @Payload String offer) {
        taskExecutor.taskExecutor().execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/offer/%s", sessionId), offer);
            log.info("Offer sent for session {}: {}", sessionId, offer);
        });
    }

    @Async
    @MessageMapping("/sendAnswer/{sessionId}")
    public void sendAnswer(@DestinationVariable String sessionId, @Payload String answer) {
        taskExecutor.taskExecutor().execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/answer/%s", sessionId), answer);
            log.info("Answer sent for session {}: {}", sessionId, answer);
        });
    }

    @Async
    @MessageMapping("/sendCandidate/{sessionId}")
    public void sendCandidate(@DestinationVariable String sessionId, @Payload String candidate) {
        taskExecutor.taskExecutor().execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/candidate/%s", sessionId), candidate);
            log.info("Candidate sent for session {}: {}", sessionId, candidate);
        });
    }
}
