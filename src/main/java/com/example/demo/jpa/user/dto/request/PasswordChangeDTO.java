package com.example.demo.jpa.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {
    private String username;       // 사용자 이름
    private String oldPassword; // 기존 비밀번호
    private String newPassword; // 새 비밀번호
}
