package com.example.demo.security.filter;

import com.example.demo.jpa.user.dto.request.LoginDTO;
import com.example.demo.security.authentication.CustomAuthenticationManager;
import com.example.demo.security.jwt.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final CustomAuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationFilter(CustomAuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = new ObjectMapper(); // ObjectMapper 초기화
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청 본문에서 로그인 정보 파싱
            LoginDTO loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
            log.info("로그인 시도: {}", loginDTO);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("인증 실패: 요청 본문을 읽을 수 없습니다.", e);
            throw new AuthenticationException("Failed to parse login request", e) {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // JWT 토큰 생성 및 응답에 추가
        String username = authResult.getName();
        String roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        String token = jwtTokenUtil.generateToken(username, roles);
        response.setHeader("authorization", "Bearer " + token);
        response.setHeader("userName", username);

        log.info("인증 성공: {} (권한: {})", username, roles);

        // HTTP 상태만 반환
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("인증 실패: {}", failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Authentication Failed: " + failed.getMessage());
    }


    // JSON 응답 작성 메서드
    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, AuthenticationResponse authResponse) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(authResponse));
    }

    // JSON 응답 객체 정의
    private static class AuthenticationResponse {
        private final String status;
        private final String username;
        private final String roles;
        private final String token;

        public AuthenticationResponse(String status, String username, String roles, String token) {
            this.status = status;
            this.username = username;
            this.roles = roles;
            this.token = token;
        }

        public String getStatus() {
            return status;
        }

        public String getUsername() {
            return username;
        }

        public String getRoles() {
            return roles;
        }

        public String getToken() {
            return token;
        }
    }
}
