package com.example.demo.jpa.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // 비밀번호 해싱 메서드
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); // salt 추가
            byte[] hashedPassword = md.digest(password.getBytes());

            // 해시된 비밀번호와 salt를 Base64로 인코딩하여 저장
            return Base64.getEncoder().encodeToString(salt) + "$" +
                    Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }

    // salt 생성 메서드
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt 생성
        random.nextBytes(salt);
        return salt;
    }

    // 비밀번호 검증 메서드
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        String[] parts = storedPassword.split("\\$");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        String inputHash = hashPassword(inputPassword, salt);
        return inputHash.equals(storedPassword);
    }
}
