package com.example.demo.jpa.socket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ScreenShareController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Async
    @MessageMapping("/sendOffer")
    public void sendOffer(@Payload String offer) {
        taskExecutor.execute(() -> {
            messagingTemplate.convertAndSend("/topic/offer", offer);
            log.info("Offer sent: {}", offer);
        });
    }

    @Async
    @MessageMapping("/sendAnswer")
    public void sendAnswer(@Payload String answer) {
        taskExecutor.execute(() -> {
            messagingTemplate.convertAndSend("/topic/answer", answer);
            log.info("Answer sent: {}", answer);
        });
    }

    @Async
    @MessageMapping("/sendCandidate")
    public void sendCandidate(@Payload String candidate) {
        taskExecutor.execute(() -> {
            messagingTemplate.convertAndSend("/topic/candidate", candidate);
            log.info("Candidate sent: {}", candidate);
        });
    }
}
