package com.example.demo.jpa.user.controller;

import com.example.demo.jpa.eclass.entity.EClassUuid;
import com.example.demo.jpa.eclass.repository.EClassUuidRepository;
import com.example.demo.jpa.socket.model.entity.Session;
import com.example.demo.jpa.socket.repository.SessionRepository;
import com.example.demo.jpa.user.dto.request.LoginDTO;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private final EClassUuidRepository eClassUuidRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> LoginMethod(HttpServletRequest request , HttpServletResponse response){

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
//            log.info("Header: {} = {}", headerName, headerValue);
            response.addHeader(headerName, headerValue);
        }
        return ResponseEntity.ok().body("loginSuccess");
    }

    @GetMapping("/api/student/get/{username}")
    public ResponseEntity<Optional<User>> GetStudentData(@PathVariable String username){

        log.info("학생 이름 확인 : " + username);

        Optional<User> userData = userRepository.findByUsername(username);

        return ResponseEntity.ok().body(userData);
    }

    // 해당 EClass에 일대일 관계로 들어가있는 학생의 pk Id 가져오는 메서드 (Table: EClassUuidTable)
    @GetMapping("/api/student/getStudentId")
    public ResponseEntity<Long> getStudentId(@RequestParam String username, @RequestParam String uuid) {
        Optional<User> studentUser = userService.findByName(username);
        log.info("학생 확인 : " + studentUser.get().getId());
        log.info("uuid 확인 : " + uuid);

        Optional<EClassUuid> EclassStudent =  eClassUuidRepository.findIdByEclassUuidAndStudentId(uuid,studentUser.get().getId());
        long EclassStudentId = EclassStudent.get().getId();

        log.info("EclassStudentId 확인 : " + EclassStudentId);

        return ResponseEntity.ok().body(EclassStudentId);

    }


}
