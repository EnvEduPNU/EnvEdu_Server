package com.example.demo.jpa.openapi.model.entity;

import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.openapi.model.parent.OceanQualityParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "OceanQuality")
public class OceanQuality extends OceanQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }
    public OceanQuality(){
        super();
    }

}
