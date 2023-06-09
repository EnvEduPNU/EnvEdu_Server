package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.AirQualityParent;
import com.example.demo.user.model.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "AirQuality",
        uniqueConstraints = @UniqueConstraint(columnNames = {"stationName"}))
public class AirQuality extends AirQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    //@NotNull
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
