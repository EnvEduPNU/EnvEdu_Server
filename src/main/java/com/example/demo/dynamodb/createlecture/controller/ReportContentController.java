package com.example.demo.dynamodb.createlecture.controller;


import com.example.demo.dynamodb.createlecture.entity.ReportStepContent;
import com.example.demo.dynamodb.createlecture.service.ReportStepContentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/report")
// 학생의 보고서 저장 클래스, 각 path의 역할에 맞게 메서드 수행중 (ex save -> 보고서 저장)
public class ReportContentController {

    private final ReportStepContentServiceImpl reportStepContentService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveStepContent(@RequestBody List<ReportStepContent> reportStepContents) {
        try {
            ReportStepContent formattedReportStepContent = reportStepContents.get(0);
            log.info("save 들어온 것들 확인 : " + formattedReportStepContent.getStepName());
            reportStepContentService.saveStepContents(formattedReportStepContent);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error saving report step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStepContent(@RequestBody List<ReportStepContent> assignmentStepContent) {
        try {
            ReportStepContent formattedReportStepContent = assignmentStepContent.get(0);
            reportStepContentService.updateStepContents(
                    formattedReportStepContent.getUuid(),
                    formattedReportStepContent.getTimestamp(),
                    formattedReportStepContent
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating report step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<ReportStepContent>> getStepContent() {
        try {
            List<ReportStepContent> stepContents = reportStepContentService.getAllStepContents();
            return ResponseEntity.ok(stepContents);
        } catch (Exception e) {
            log.error("Error retrieving report step content", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/getstep")
    public ResponseEntity<List<ReportStepContent>> getStepContentByUuid(@RequestBody List<String> uuids) {
        try {
            log.info("Lecture Content Called for UUID: " + uuids.toString());
            List<ReportStepContent> reportStepContentList = reportStepContentService.getStepContentByUuid(uuids);
            return ResponseEntity.ok(reportStepContentList);
        } catch (Exception e) {
            log.error("Error retrieving step content by UUIDs", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{uuid}/{timestamp}")
    public ResponseEntity<Void> deleteLectureContent(@PathVariable String uuid, @PathVariable String timestamp) {
        try {
            reportStepContentService.deleteContent(uuid, timestamp);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting lecture content", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
