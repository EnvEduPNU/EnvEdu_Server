package com.example.demo.jpa.user.controller;

import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.repository.SessionRepository;
import com.example.demo.jpa.user.dto.request.LoginDTO;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @PostMapping("/login")
    public ResponseEntity<?> LoginMethod(HttpServletRequest request , HttpServletResponse response){

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {} = {}", headerName, headerValue);
            response.addHeader(headerName, headerValue);
        }
        return ResponseEntity.ok().body("loginSuccess");
    }

    @GetMapping("/student")
    public ResponseEntity<?> GetStudentList(@RequestParam String sessionId){

        log.info("확인 : " + sessionId);

        Session studentSession = sessionRepository.findBySessionId(sessionId);

        log.info("세션 아이디 : " +studentSession.getId());
        Optional<User> student = userRepository.findBySessionId(studentSession.getId());

        log.info("학생 확인 : " + student.toString());

        return ResponseEntity.ok().body(student);

    }

}
