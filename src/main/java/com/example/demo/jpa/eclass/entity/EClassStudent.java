package com.example.demo.jpa.eclass.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EClassStudent")
@Data
public class EClassStudent {

    @Id
    @Column(name = "studentId", nullable = false)
    private Long studentId;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(name = "studentGroup", nullable = false)
    private String studentGroup;

    @Column(name = "joinDate", nullable = false)
    private String joinDate;

}

