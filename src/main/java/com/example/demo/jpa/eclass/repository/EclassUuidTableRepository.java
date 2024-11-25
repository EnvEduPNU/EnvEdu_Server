package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClass;
import com.example.demo.jpa.eclass.entity.EClassUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EclassUuidTableRepository extends JpaRepository<EClassUuid, String> {
    boolean existsByEclassUuidAndStudentId(String eClassUuid, Long studentId);
    void deleteByEclassUuidAndStudentId(String eClassUuid, Long studentId);
}
