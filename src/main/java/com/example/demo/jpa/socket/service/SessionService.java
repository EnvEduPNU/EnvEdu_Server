package com.example.demo.jpa.socket.service;

import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.repository.SessionRepository;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    // Save a new session
    @Transactional
    public String saveSession(Session session) {
        // 유저 정보 조회
        Optional<User> userInfo = userRepository.findByUsername(session.getUserName());

        if (userInfo.isEmpty()) {
            log.warn("User not found: {}", session.getUserName());
            return null;
        }

        User user = userInfo.get();
        String originalSessionId = null;

        if (user.getSessionId() != null) {
            // 기존 세션이 있는 경우, 기존 세션 정보 조회
            Optional<Session> existingSessionOpt = sessionRepository.findById(user.getSessionId());
            if (existingSessionOpt.isPresent()) {
                originalSessionId = existingSessionOpt.get().getSessionId();
                log.info("Updated existing session for user: {}", user.getUsername());
            } else {
                log.warn("Session not found for sessionId: {}", user.getSessionId());
            }
        }

        // 세션 저장 또는 업데이트
        Session savedSession = sessionRepository.save(session);
        log.info("Session saved with ID: {}", savedSession.getId());

// 유저의 세션 ID 업데이트 후 저장
        user.setSessionId(session.getId());

        log.info("user 세션아이디 : {}", user.toString());

        User savedUser = userRepository.save(user);
        log.info("User saved with updated session ID: {}", savedUser.getSessionId());


        log.info("Saved new session for user: {}", user.getUsername());

        return originalSessionId;
    }


    // Retrieve all session IDs
    @Transactional
    public List<String> getAllSessionIds() {
        return sessionRepository.findAll().stream()
                .map(Session::getSessionId)
                .collect(Collectors.toList());


    }

    @Transactional
    public void deleteSession(String sessionName) {
        log.info("삭제할 세션 이름 : {}", sessionName);

        Session session = sessionRepository.findBySessionId(sessionName);

        if(session !=null){
            Optional<User> userOptional = userRepository.findBySessionId(session.getId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // User 엔티티의 session을 null로 설정하여 세션 참조 제거
                user.setSessionId(null);
                userRepository.save(user); // 변경 사항 저장

                // 이제 Session 엔티티를 삭제
                sessionRepository.deleteBySessionId(session.getSessionId());
            }
        }else{
            log.info("삭제할 세션이 없음");
        }

    }
}
