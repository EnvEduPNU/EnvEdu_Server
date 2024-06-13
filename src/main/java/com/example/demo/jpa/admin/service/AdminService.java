package com.example.demo.jpa.admin.service;

import com.example.demo.jpa.admin.DTO.AdminLoginDTO;
import com.example.demo.jpa.admin.cipher.AdminCipher;
import com.example.demo.jpa.admin.model.Admin;
import com.example.demo.jpa.admin.repository.AdminRepository;
import com.example.demo.jpa.util.CookieUtil;
import com.example.demo.jpa.jwt.model.JwtRefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminCipher adminCipher;

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public ResponseCookie loginAdmin(AdminLoginDTO adminLoginDTO) {
        Admin admin = adminRepository.findByUsername(adminLoginDTO.getUsername()).orElseThrow(()->new IllegalArgumentException("로그인 정보가 일치하지 않습니다"));
        if(!adminCipher.encrypt(adminLoginDTO.getPassword()).equals(admin.getPassword())) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다");
        }

        JwtRefreshToken jwtRefreshToken = JwtRefreshToken.generateJwtRefreshToken(admin);

        return CookieUtil.generateCookieForRefreshToken(jwtRefreshToken);
    }

    @Transactional(readOnly = true)
    public Admin findAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("해당 어드민이 존재하지 않습니다"));
    }
}
