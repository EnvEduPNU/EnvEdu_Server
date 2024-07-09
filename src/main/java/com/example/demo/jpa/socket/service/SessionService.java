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
        log.info("서비스 세션아이디 : {}", session.getSessionId());
        log.info("서비스 유저 이름 : {}", session.getUserName());

        Optional<User> userInfo = userRepository.findByUsername(session.getUserName());
        final String[] originalSessionId = {null};

        // 람다 표현식이나 익명 클래스 내부에서 로컬 변수 변경 못하기 때문에 배열로 우회
        userInfo.ifPresent(user -> {
            if (user.getSessionId() != null) {
                // 기존 세션이 존재하는 경우, 세션을 업데이트
                Optional<Session> existingSessionOpt = sessionRepository.findById(user.getSessionId());
                existingSessionOpt.ifPresent(existingSession -> {
                    originalSessionId[0] = existingSession.getSessionId();
                    log.info("Updated existing session for user: {}", user.getUsername());
                });
            } else {
                // 기존 세션이 없는 경우, 새로운 세션을 저장
                sessionRepository.save(session);
                user.setSessionId(session.getId());
                userRepository.save(user);
                log.info("Saved new session for user: {}", user.getUsername());
            }
        });

        return originalSessionId[0];
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
