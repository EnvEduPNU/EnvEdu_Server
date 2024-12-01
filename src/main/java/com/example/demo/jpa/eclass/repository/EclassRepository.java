package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EclassRepository extends JpaRepository<EClass, String> {

    @Query("SELECT e FROM EClass e WHERE e.eClassUuid = :eClassUuid AND e.eclassStart = true")
    Optional<EClass> findStartedEClassByUuid(String eClassUuid);

    @Query("SELECT e FROM EClass e WHERE e.lectureDataUuid = :lectureDataUuid")
    Optional<EClass> findEClassByLectureUuid(String lectureDataUuid);


    @Query("SELECT e FROM EClass e WHERE e.eClassUuid = :eClassUuid")
    Optional<EClass> findEClassByEClassUuid(String eClassUuid);


}
