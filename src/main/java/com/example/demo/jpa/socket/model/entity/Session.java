package com.example.demo.jpa.socket.model.entity;

import com.example.demo.jpa.socket.util.TableChangeListener;
import com.example.demo.jpa.user.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EntityListeners(TableChangeListener.class)
@Entity
@Table(name = "sessions")
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private String userName;

    // Constructor
    public Session() {}

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

}
