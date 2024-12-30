package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secret;
    private SecretKey secretKey;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * JWT 토큰 생성
     * @param username 사용자 이름
     * @param role 사용자 역할
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // 역할 정보를 클레임으로 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 72000000)) // 20시간 유효
                .signWith(secretKey)
                .compact();
    }

    /**
     * JWT 토큰 검증 및 클레임 반환
     * @param token JWT 토큰
     * @return 토큰의 클레임
     */
    public Claims validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            // 만료 시간 확인
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("JWT token is expired");
            }

            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    /**
     * JWT 토큰에서 사용자 이름 추출
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String getUsernameFromToken(String token) {
        return validateToken(token).getSubject();
    }

    /**
     * JWT 토큰에서 역할 정보 추출
     * @param token JWT 토큰
     * @return 사용자 역할
     */
    public String getRoleFromToken(String token) {
        return validateToken(token).get("role", String.class);
    }
}
