package com.example.demo.admin.service;

import com.example.demo.admin.DTO.AdminLoginDTO;
import com.example.demo.admin.cipher.AdminCipher;
import com.example.demo.admin.model.Admin;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.jwt.model.JwtAccessToken;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Value("${spring.server.domain}")
    private String domain;

    private final AdminCipher adminCipher;

    private final AdminRepository adminRepository;

    public Cookie loginAdmin(AdminLoginDTO adminLoginDTO) {
        Admin admin = adminRepository.findByUsername(adminLoginDTO.getUsername()).orElseThrow(()->new IllegalArgumentException("로그인 정보가 일치하지 않습니다"));
        if(!adminCipher.encrypt(adminLoginDTO.getPassword()).equals(admin.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다");
        }

        JwtAccessToken jwtAccessToken = JwtAccessToken.generateJwtAccessToken(admin);
        Cookie cookie = new Cookie("access_token", JwtUtil.convertJwtToString(jwtAccessToken));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(JwtAccessToken.validTimeInSec);

        return cookie;
    }
}
