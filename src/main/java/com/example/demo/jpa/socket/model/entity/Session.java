package com.example.demo.jpa.socket.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
@Getter
@Setter
public class Session {
    @Id
    private String sessionId;

    // Constructor
    public Session() {}

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

}
