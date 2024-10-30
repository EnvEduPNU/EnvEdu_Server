package com.example.demo.jpa.user.util;

import com.example.demo.jpa.user.model.entity.User;

public class UserFactory {
    public static User createUser(String userType) {
        if ("newUser".equals(userType)) {
            return new User() {
            };
        }
        throw new IllegalArgumentException("알 수 없는 사용자 유형");
    }
}

