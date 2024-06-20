package com.example.demo.jpa.socket.service;

import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;

    // Save a new session
    public void saveSession(Session session) {
        log.info("서비스 세션아이디 : {}", session.getSessionId());
        sessionRepository.save(session);
    }

    // Retrieve all session IDs
    @Transactional
    public List<String> getAllSessionIds() {
        return sessionRepository.findAll().stream()
                .map(Session::getSessionId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSession(String sessionId) {
        log.info("세션아이디 : {}", sessionId);
        sessionRepository.deleteById(sessionId);
    }
}
