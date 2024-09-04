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

    private final SessionService sessionService;

    @PostMapping("/register-session")
    public ResponseEntity<String> registerSession(@RequestBody Session session) {
        log.info("[register-session] 세션아이디 : {}", session.getSessionId());
        log.info("[register-session] 이름  : {}", session.getUserName());

        String sessionName = sessionService.saveSession(session);
        log.info("[register-session] sessionName  : {}", sessionName);

        return ResponseEntity.ok(sessionName);
    }

    @GetMapping("/get-session-ids")
    public ResponseEntity<List<String>> getSessionIds() {
        List<String> sessionIds = sessionService.getAllSessionIds();
        log.info("받아온 세션 아이디들 : " + sessionIds);
        return ResponseEntity.ok(sessionIds);
    }

    @DeleteMapping("/delete-session/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId) {
        log.info("세션아이디 : {}", sessionId);
        sessionService.deleteSession(sessionId);
        return ResponseEntity.ok("Session deleted successfully");
    }

}
