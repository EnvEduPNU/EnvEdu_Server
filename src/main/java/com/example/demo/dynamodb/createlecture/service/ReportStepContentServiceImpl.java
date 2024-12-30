package com.example.demo.dynamodb.createlecture.service;

import com.example.demo.dynamodb.createlecture.entity.ReportStepContent;
import com.example.demo.dynamodb.createlecture.repository.ReportStepContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportStepContentServiceImpl implements ReportStepContentService {

    private final ReportStepContentRepository reportStepContentRepository;

    @Override
    public void saveStepContents(ReportStepContent reportStepContent) {
        reportStepContentRepository.save(reportStepContent);
    }

    @Override
    public List<ReportStepContent> getAllStepContents() {
        return reportStepContentRepository.findAll();
    }

    @Override
    public void deleteContent(String uuid, String timestamp) {
        reportStepContentRepository.deleteByStepName(uuid, timestamp);
    }

    @Override
    public void updateStepContents(String uuid, String timestamp, ReportStepContent reportStepContent) {
        reportStepContentRepository.updateStepContent(uuid, timestamp, reportStepContent);
    }

    @Override
    public List<ReportStepContent> getStepContentByUuid(List<String> uuid) {
        return reportStepContentRepository.getStepContentByUuid(uuid);
    }
}
