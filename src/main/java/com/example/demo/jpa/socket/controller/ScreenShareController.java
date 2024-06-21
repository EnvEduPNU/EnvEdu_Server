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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ScreenShareController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ThreadConfig taskExecutor;

    private final ConcurrentHashMap<String, ExecutorService> sessionExecutors = new ConcurrentHashMap<>();

    private ExecutorService getSessionExecutor(String sessionId) {
        return sessionExecutors.computeIfAbsent(sessionId, k -> Executors.newSingleThreadExecutor());
    }

    @Async
    @MessageMapping("/sendOffer/{sessionId}")
    public void sendOffer(@DestinationVariable String sessionId, @Payload String offer) {
        ExecutorService executorService = getSessionExecutor(sessionId);
        executorService.execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/offer/%s", sessionId), offer);
            log.info("Offer sent for session {}: {}", sessionId, offer);
        });
    }

    @Async
    @MessageMapping("/sendAnswer/{sessionId}")
    public void sendAnswer(@DestinationVariable String sessionId, @Payload String answer) {
        ExecutorService executorService = getSessionExecutor(sessionId);
        executorService.execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/answer/%s", sessionId), answer);
            log.info("Answer sent for session {}: {}", sessionId, answer);
        });
    }

    @Async
    @MessageMapping("/sendCandidate/{sessionId}")
    public void sendCandidate(@DestinationVariable String sessionId, @Payload String candidate) {
        ExecutorService executorService = getSessionExecutor(sessionId);
        executorService.execute(() -> {
            messagingTemplate.convertAndSend(String.format("/topic/candidate/%s", sessionId), candidate);
            log.info("Candidate sent for session {}: {}", sessionId, candidate);
        });
    }
}
