package com.example.demo.datacontrol.datachunk.repository;

import com.example.demo.datacontrol.datachunk.model.DataCompilation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataCompilationRepository extends JpaRepository<DataCompilation, Long> {
    List<DataCompilation> findAllByOwnerIdOrderBySaveDate(Long id);

}
