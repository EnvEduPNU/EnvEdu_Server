package com.example.demo.dynamodb.createlecture.controller;


import com.example.demo.dynamodb.createlecture.entity.StepContent;
import com.example.demo.dynamodb.createlecture.entity.ThumbImgDTO;
import com.example.demo.dynamodb.createlecture.service.StepContentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/steps")
// 교사의 eclass 생성 쪽 클래스
public class StepContentController {

    private final StepContentServiceImpl stepContentService;

    // eclass 생성 메서드
    @PostMapping("/saveLectureContent")
    public ResponseEntity<Void> saveStepContent(@RequestBody StepContent stepContents) {
        try {
            stepContentService.saveStepContents(stepContents);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error saving step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // eclass 수정 메서드
    @PatchMapping("/updateLectureContent")
    public ResponseEntity<Void> updateStepContent(@RequestBody StepContent stepContents) {
        try {
            stepContentService.updateStepContents(stepContents.getUuid(), stepContents.getTimestamp(), stepContents);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // eclass 썸네일 수정 메서드
    @PatchMapping("/updateThumbImg")
    public ResponseEntity<Void> updateThumbImg(@RequestBody ThumbImgDTO thumbImgDTO) {
        try {
            stepContentService.updateThumbImg(thumbImgDTO.getUuid(), thumbImgDTO.getTimestamp(), thumbImgDTO.getThumbImg());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating thumbnail image", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 모든 수업 자료 조회 메서드
    @GetMapping("/getLectureContent")
    public ResponseEntity<List<StepContent>> getStepContent() {
        try {
            List<StepContent> stepContents = stepContentService.getAllStepContents();
            return ResponseEntity.ok(stepContents);
        } catch (Exception e) {
            log.error("Error retrieving step contents", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 수업자료 uuid로 하나의 수업 자료만 조회 해오는 메서드
    @GetMapping("/getLectureContentOne")
    public ResponseEntity<StepContent> getStepContentOne(@RequestParam String uuid) {
        try {
            StepContent stepContent = stepContentService.getStepContent(uuid);
            return ResponseEntity.ok(stepContent);
        } catch (Exception e) {
            log.error("Error retrieving step content by UUID", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 수업 자료 삭제 메서드
    @DeleteMapping("/deleteLectureContent/{uuid}/{timestamp}")
    public ResponseEntity<Void> deleteLectureContent(@PathVariable String uuid, @PathVariable String timestamp) {
        try {
            stepContentService.deleteContent(uuid, timestamp);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
