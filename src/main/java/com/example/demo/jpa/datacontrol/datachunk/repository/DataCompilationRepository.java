package com.example.demo.jpa.datacontrol.datachunk.repository;

import com.example.demo.jpa.datacontrol.datachunk.model.DataCompilation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DataCompilationRepository extends JpaRepository<DataCompilation, Long> {
    List<DataCompilation> findAllByOwnerIdOrderBySaveDate(Long id);

    // ownerId로 데이터를 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM DataCompilation dc WHERE dc.id = :id")
    void deleteById(@Param("id") Long id);
}
