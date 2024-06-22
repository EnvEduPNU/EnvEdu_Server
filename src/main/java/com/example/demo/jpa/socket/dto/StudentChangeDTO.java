package com.example.demo.jpa.socket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentChangeDTO {
    private String sessionId;
    private String action;

    public StudentChangeDTO(String sessionId, String action) {
        this.sessionId = sessionId;
        this.action = action;
    }

}