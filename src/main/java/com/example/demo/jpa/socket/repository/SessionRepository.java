package com.example.demo.jpa.socket.repository;

import com.example.demo.jpa.socket.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findBySessionId(String sessionName);

    void deleteBySessionId(String sessionId);

    Optional<Session> findByEclassUuidAndUserName(String eclassUuid, String userName);

    Optional<Session> findBySessionIdAndEclassUuid(String sessionId, String eclassUuid);

    List<Session> findSessionIdByEclassUuid(String eclassUuid);
}
