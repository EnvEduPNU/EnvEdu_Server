package com.example.demo.user.repository;

import com.example.demo.user.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByUsernameIn(List<String> usernames);
}
