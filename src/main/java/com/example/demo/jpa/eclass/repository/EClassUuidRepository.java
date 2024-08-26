package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClassUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EClassUuidRepository extends JpaRepository<EClassUuid, Long> {
    List<EClassUuid> findByEclassUuid(String eclassUuid);

    List<EClassUuid> findByStudentId(Long studentId);

    boolean existsByStudentIdAndEclassUuid(Long studentId, String eclassUuid);

    void deleteByEclassUuid(String eclassUuid);

    boolean existsByEclassUuid(String eclassUuid);
    Optional<EClassUuid> findIdByEclassUuidAndStudentId(String eclassUuid, Long studentId);

}
