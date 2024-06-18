package com.example.demo.jpa.user.controller;

import com.example.demo.jpa.user.dto.request.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

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
            response.addHeader(headerName, headerValue);
        }
        return ResponseEntity.ok().body("gogoSuccess!!");
    }

    @PostMapping("/test")
    public ResponseEntity<String> postTestMethod(@RequestBody LoginDTO body) {

        log.info("Request body: {}", body);
        return ResponseEntity.ok("gogoSuccess!!");
    }

    @GetMapping("/test")
    public ResponseEntity<?> GetTestMethod(HttpServletRequest request , HttpServletResponse response){

        log.info("잘 넘어옴 : {}", request);

        return ResponseEntity.ok().body("gogoSuccess!!");
    }
}
