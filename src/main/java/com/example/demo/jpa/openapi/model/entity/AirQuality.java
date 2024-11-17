package com.example.demo.jpa.openapi.model.entity;

import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.openapi.model.parent.AirQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "AirQuality")
public class AirQuality extends AirQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;


    public void setOwner(User owner) {
        this.owner = owner;
    }

    public AirQuality() {
        super();
    }
}
