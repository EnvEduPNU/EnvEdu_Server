package com.example.demo.jpa.eclass.entity;

import lombok.Data;

import javax.persistence.*;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "EClassUuidTable")
@Data
public class EClassUuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eclassUuid", nullable = false)
    private String eclassUuid;

    @Column(name = "assignmentUuid", nullable = true)
    private String assignmentUuid;

    @Column(name = "studentId", nullable = false)
    private Long studentId;

    @ElementCollection
    @CollectionTable(name = "AssignmentData", joinColumns = @JoinColumn(name = "eclassUuid_id"))
    @Column(name = "assignmentData", nullable = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL) // Hibernate의 CascadeType 추가
    private List<Boolean> assignmentData;

    @Column(name = "reportData", nullable = true)
    private String reportData;
}

