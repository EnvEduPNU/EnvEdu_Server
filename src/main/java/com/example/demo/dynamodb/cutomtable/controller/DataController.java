package com.example.demo.dynamodb.cutomtable.controller;

import com.example.demo.dynamodb.cutomtable.entity.DataEntity;
import com.example.demo.dynamodb.cutomtable.service.DataService;
import com.example.demo.dynamodb.cutomtable.dto.DataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
// 커스텀 데이터 저장/삭제/조회 메서드 (AWS DynamoDB에 저장되며 테이블 이름: CustomData)
@RequestMapping("/api/custom")
public class DataController {

    private final DataService dataService;

    // 데이터 저장 메서드
    @PostMapping("/save")
    public ResponseEntity<Void> saveData(@RequestBody DataDTO dataDTOs) {
        try {
            dataService.saveData(dataDTOs);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error saving data: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // 데이터 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable String id) {
        try {
            dataService.deleteData(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // 사용자 이름으로 데이터 조회 메서드
    @GetMapping("/list")
    public ResponseEntity<List<DataEntity>> getAllData(@RequestParam String username) {
        try {
            List<DataEntity> dataList = dataService.findAllByUserName(username);
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            log.error("Error retrieving data list: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ID로 데이터 조회 메서드
    @GetMapping("/{id}")
    public ResponseEntity<DataEntity> getDataById(@PathVariable String id) {
        try {
            Optional<DataEntity> data = dataService.findById(id);
            return data.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error retrieving data by ID: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
