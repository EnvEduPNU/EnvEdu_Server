package com.example.demo.jpa.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Controller
@Slf4j
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<?> LoginMethod(HttpServletRequest request , HttpServletResponse response){

        log.info("잘 넘어옴 : {}", request);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {} = {}", headerName, headerValue);
            response.addHeader(headerName, headerValue); // 요청 헤더를 응답 헤더로 복사
        }

//        String userName = request.getHeader("username");
//        log.info("username : {}", userName);
//
//        String authorization = request.getHeader("authorization");
//        log.info("authorization : {}", authorization);


        return ResponseEntity.ok().body("gogoSuccess!!");
    }

    @GetMapping("/test")
    public ResponseEntity<?> TestMethod(HttpServletRequest request , HttpServletResponse response){

        log.info("잘 넘어옴 : {}", request);

        return ResponseEntity.ok().body("gogoSuccess!!");
    }
}
