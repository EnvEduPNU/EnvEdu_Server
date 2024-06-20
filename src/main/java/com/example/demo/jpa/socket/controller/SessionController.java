package com.example.demo.jpa.socket.controller;

import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/sessions")
public class SessionController {

    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/register-session")
    public ResponseEntity<String> registerSession(@RequestBody Session sessionId) {
        log.info("세션아이디 : {}", sessionId.getSessionId());
        sessionService.saveSession(sessionId.getSessionId());
        return ResponseEntity.ok("Session registered successfully");
    }

    @GetMapping("/get-session-ids")
    public ResponseEntity<List<String>> getSessionIds() {
        List<String> sessionIds = sessionService.getAllSessionIds();
        return ResponseEntity.ok(sessionIds);
    }

    @DeleteMapping("/delete-session")
    public ResponseEntity<String> deleteSession(@RequestBody Session sessionId) {
        log.info("세션아이디 : {}", sessionId.getSessionId());
        sessionService.deleteSession(sessionId.getSessionId());
        return ResponseEntity.ok("Session deleted successfully");
    }
}
