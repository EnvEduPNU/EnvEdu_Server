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

        // 기존 세션이 있는지 확인
        if (user.getSessionId() != null) {
            // 기존 세션 정보 조회
            Optional<Session> existingSessionOpt = sessionRepository.findById(user.getSessionId());

            if (existingSessionOpt.isPresent()) {
                Session existingSession = existingSessionOpt.get();
                originalSessionId = existingSession.getSessionId();

                // 기존 eclassUuid와 새로운 eclassUuid 비교
                if (existingSession.getEclassUuid().equals(session.getEclassUuid())) {
                    // eclassUuid가 동일한 경우, 기존 세션 업데이트
                    existingSession.setSessionId(session.getSessionId());
                    sessionRepository.save(existingSession);
                    log.info("Updated existing session for user: {}", user.getUsername());
                    return originalSessionId; // 기존 세션 ID 반환
                } else {
                    // eclassUuid가 다를 경우, 새로운 세션 저장
                    log.info("eclassUuid가 다릅니다. 새로운 세션을 생성합니다.");
                }
            } else {
                log.warn("Session not found for sessionId: {}", user.getSessionId());
            }
        }

        // 새로운 세션 저장
        Session savedSession = sessionRepository.save(session);
        log.info("Session saved with ID: {}", savedSession.getId());

        // 유저의 세션 ID 업데이트 후 저장
        user.setSessionId(savedSession.getId());
        log.info("Updated user session ID: {}", user.toString());

        User savedUser = userRepository.save(user);
        log.info("User saved with updated session ID: {}", savedUser.getSessionId());
        log.info("Saved new session for user: {}", user.getUsername());

        return savedSession.getSessionId(); // 새로운 세션 ID 반환
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

    @Transactional
    public Boolean updateSession(Session updateSession) {
        try {
            sessionRepository.save(updateSession);

            Optional<User> userInfo = userRepository.findByUsername(updateSession.getUserName());
            User user = userInfo.get();

            // 유저의 세션 ID 업데이트 후 저장
            user.setSessionId(updateSession.getId());
            log.info("user 세션아이디 : {}", user.toString());

            User savedUser = userRepository.save(user);
            log.info("User saved with updated session ID: {}", savedUser.getSessionId());
            log.info("Saved new session for user: {}", user.getUsername());
            return true; // 업데이트 성공 시 true 반환
        } catch (Exception e) {
            // 예외 발생 시 로그를 남기고 false 반환
            log.error("세션 업데이트 중 오류 발생: {}", e.getMessage());
            return false; // 업데이트 실패 시 false 반환
        }
    }

    @Transactional
    public Optional<Session> findSessionByEclassUuidAndUserName(String eclassUuid, String userName) {
        return sessionRepository.findByEclassUuidAndUserName(eclassUuid, userName);
    }

    @Transactional
    public Optional<User> findUsersByEclassUuidAndSessionId(Session session){
        // 세션을 찾는 로직에서 Optional을 사용하여 NullPointerException 방지
        Optional<Session> studentSessionOpt = sessionRepository.findBySessionIdAndEclassUuid(session.getSessionId(), session.getEclassUuid());

        if (studentSessionOpt.isEmpty()) {
            log.warn("해당 세션을 찾을 수 없습니다. 세션 ID: {}, Eclass UUID: {}", session.getSessionId(), session.getEclassUuid());
            return Optional.empty(); // 세션을 찾지 못한 경우 빈 Optional 반환
        }

        Session studentSession = studentSessionOpt.get();
        log.info("세션 아이디 : {}", studentSession.getId());

        Optional<User> student = userRepository.findBySessionId(studentSession.getId());

        if (student.isPresent()) {
            log.info("학생 확인 : {}", student.get().getUsername()); // 사용자 이름이나 관련 정보를 출력
        } else {
            log.warn("해당 세션에 학생을 찾을 수 없습니다. 세션 ID: {}", studentSession.getId());
        }

        return student;
    }

}

