package com.example.demo.jpa.eclass.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
@ToString
@Data
public class EClassSessions {
    @Id
    @JsonProperty("id")
    private int id;

    @Column(name = "eclassUuid", nullable = false)
    private String eclassUuid;

    @Column(name = "sessionId", nullable = false)
    private String sessionId;

    @Column(name = "userName",nullable = false)
    private String userName;


}
