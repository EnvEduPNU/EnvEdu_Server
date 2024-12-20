package com.example.demo.jpa.socket.controller;

import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.service.SessionService;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    // 세선 생성 메서드
    @PostMapping("/register-session")
    public ResponseEntity<String> registerSession(@RequestBody Session session) {
        log.info("[register-session] 세션아이디 : {}", session.getSessionId());
        log.info("[register-session] 이름  : {}", session.getUserName());
        log.info("[register-session] EClass uuid : {}", session.getEclassUuid());

        String eclassUuid = session.getEclassUuid();
        String userName = session.getUserName();

        // DB에 eclassUuid와 userName이 동일한 row가 있는지 확인
        Optional<Session> existingSession = sessionService.findSessionByEclassUuidAndUserName(eclassUuid, userName);

        if (existingSession.isPresent()) {
            // 같은 유저가 이미 존재하면 sessionId만 업데이트
            Session updateSession = existingSession.get();
            updateSession.setSessionId(session.getSessionId());
            sessionService.updateSession(updateSession);  // 업데이트 로직
            return ResponseEntity.ok(updateSession.getSessionId()); // 업데이트된 sessionId 반환
        } else {
            // 없다면 새로운 세션을 저장
            String newSession = sessionService.saveSession(session);
            return ResponseEntity.ok(newSession); // 새로 생성된 sessionId 반환
        }
    }


    // eclass 에서 현재 존재하는 세션 아이디 가져오는 메서드
    @GetMapping("/get-session-ids/{eclassUuid}")
    public ResponseEntity<List<Session>> getSessionIds(@PathVariable String eclassUuid) {
        List<Session> sessionIds = sessionService.getSessionIdsByEclassUuid(eclassUuid);
        log.info("받아온 세션 아이디들 : " + sessionIds);
        return ResponseEntity.ok(sessionIds);
    }

    // 세션 아이디 삭제 메서드
    @DeleteMapping("/delete-session/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId) {
        log.info("세션아이디 : {}", sessionId);
        sessionService.deleteSession(sessionId);
        return ResponseEntity.ok("Session deleted successfully");
    }

    //학생리스트 조회 메서드
    @PostMapping("/student/get")
    public ResponseEntity<Optional<User>> GetStudentList(@RequestBody Session session){

        Optional<User> students = sessionService.findUsersByEclassUuidAndSessionId(session);

        return ResponseEntity.ok().body(students);
    }

}
