package com.example.demo.jpa.user.dto.response;

import com.example.demo.jpa.user.model.entity.User;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final Date birthday;
    private final String role;
    private final String gender;
    private final String nickname;
    private final Timestamp updatedTime;

    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        birthday = user.getBirthday();
        role = user.getRole();
        gender = user.getGender();
        updatedTime = user.getUpdatedTime();
        nickname = user.getNickname();
    }
}
