package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClassSessions;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EClassSessionsRepository extends JpaRepository<EClassSessions, Integer> {

    @Query("SELECT s.sessionId FROM EClassSessions s WHERE s.eclassUuid = :eclassUuid AND s.userName = :userName")
    Optional<String> findSessionIdByEclassUuidAndUserName(@Param("eclassUuid") String eclassUuid, @Param("userName") String userName);
}
