package com.example.demo.security.config;

import com.example.demo.security.authentication.CustomAuthenticationManager;
import com.example.demo.security.filter.AuthenticationFilter;
import com.example.demo.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final JwtTokenUtil jwtTokenUtil; // JwtTokenUtil을 DI

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().disable() // Frame Options 비활성화
                .and()
                .csrf().disable() // CSRF 비활성화
                .cors().configurationSource(corsConfigurationSource()) // CORS 설정
                .and()
                .authorizeRequests() // 요청 권한 설정 시작
                .antMatchers("/**").permitAll() // 모든 경로 허용
                .anyRequest().authenticated() // 그 외 경로는 인증 필요
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Custom Filter 추가
                .formLogin().disable() // Form Login 비활성화
                .httpBasic().disable(); // HTTP Basic 인증 비활성화

        return http.build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter filter = new AuthenticationFilter(customAuthenticationManager, jwtTokenUtil);
        filter.setAuthenticationManager(customAuthenticationManager);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", // 로컬 개발 환경
                "https://custom.greenseed.or.kr" // 프로덕션 환경
        )); // 여러 출처 허용
        configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
        configuration.setExposedHeaders(Arrays.asList("authorization", "userName")); // 노출할 헤더 추가

        configuration.setAllowCredentials(true); // 인증 정보 포함 허용
        configuration.setMaxAge(3600L); // Preflight 요청 결과 캐싱 시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용

        return source;
    }


}
