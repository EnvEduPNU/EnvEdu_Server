package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EclassStudentRepository extends JpaRepository<EClassStudent, Long> {
    // 메서드 이름을 필드 이름과 일치시킵니다

    Optional<EClassStudent> findByStudentName(String studentName);

    EClassStudent findByStudentId(Long studentId);

    void deleteByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);

}
