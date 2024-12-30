package com.example.demo.dynamodb.createlecture.service;

import com.example.demo.dynamodb.createlecture.entity.ReportStepContent;

import java.util.List;

public interface ReportStepContentService {

    void saveStepContents(ReportStepContent reportStepContent);

    List<ReportStepContent> getAllStepContents();

    void deleteContent(String uuid, String timestamp);

    void updateStepContents(String uuid, String timestamp, ReportStepContent reportStepContent);

    List<ReportStepContent> getStepContentByUuid(List<String> uuid);
}
