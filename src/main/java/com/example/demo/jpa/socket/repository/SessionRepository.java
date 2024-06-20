package com.example.demo.jpa.socket.repository;

import com.example.demo.jpa.socket.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {


}
