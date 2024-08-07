package com.example.demo.jpa.eclass.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EClassUuidTable")
@Data
public class EClassUuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eclassUuid", nullable = false)
    private String eclassUuid;

    @Column(name = "studentId", nullable = false)
    private Long studentId;
}
