package com.example.demo.jpa.eclass.repository;

import com.example.demo.jpa.eclass.entity.EClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EclassRepository extends JpaRepository<EClass, String> {
}
