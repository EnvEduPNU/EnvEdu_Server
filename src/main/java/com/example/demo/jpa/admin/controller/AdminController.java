//package com.example.demo.jpa.admin.controller;
//
//import com.example.demo.jpa.admin.DTO.AdminLoginDTO;
//import com.example.demo.jpa.admin.service.AdminService;
//import com.example.demo.jpa.jwt.model.JwtAccessToken;
//import com.example.demo.jpa.jwt.model.JwtRefreshToken;
//import com.example.demo.jpa.jwt.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//@RequiredArgsConstructor
//public class AdminController {
//    private final AdminService adminService;
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//}
