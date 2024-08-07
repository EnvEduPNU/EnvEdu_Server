package com.example.demo.jpa.eclass.controller;

import com.example.demo.jpa.eclass.entity.EClass;
import com.example.demo.jpa.eclass.service.EclassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/eclass")
@RequiredArgsConstructor
@Slf4j
public class EclassController {

    private final EclassService eclassService;

    //--------------------------------- E-Class 생성/조회/삭제 ---------------------------------------
    @PostMapping("/create")
    public ResponseEntity<String> createEclass(@RequestBody EClass eClass){
        log.info("body확인 : " + eClass.toString());
        eclassService.saveEclass(eClass);
        return ResponseEntity.ok("Creation Successed!");
    }
    @GetMapping("/list")
    public ResponseEntity<List<EClass>> getEclassList() {
        List<EClass> EclassList = eclassService.getEclass();
        return ResponseEntity.ok(EclassList);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEclassList(@RequestParam String eClassUuid) {
        boolean deleted = eclassService.deleteEclassByUuid(eClassUuid);
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }



}
