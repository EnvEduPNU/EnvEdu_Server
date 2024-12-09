package com.example.demo.jpa.eclass.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "eclass")
@ToString
@Data
public class EClass {
    @Id
    @JsonProperty("eClassUuid")
    private String eClassUuid;

    @Column(nullable = false)
    private String lectureDataUuid;

    @Column(nullable = false)
    private String lectureDataName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private int eClassAssginSubmitNum;

    @Column(nullable = false)
    private boolean eclassStart;

}
