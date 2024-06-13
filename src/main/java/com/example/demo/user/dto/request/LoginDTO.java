package com.example.demo.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDTO {
    private String username;
    private String password;
}
