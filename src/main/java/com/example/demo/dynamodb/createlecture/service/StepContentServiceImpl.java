package com.example.demo.dynamodb.createlecture.service;

import com.example.demo.dynamodb.createlecture.entity.StepContent;
import com.example.demo.dynamodb.createlecture.repository.StepContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StepContentServiceImpl implements StepContentService {

    private final StepContentRepository stepContentRepository;

    @Override
    public void saveStepContents(StepContent stepContent) {
        stepContentRepository.save(stepContent);
    }

    @Override
    public List<StepContent> getAllStepContents() {
        return stepContentRepository.findAll();
    }

    @Override
    public StepContent getStepContent(String uuid) {
        return stepContentRepository.findOne(uuid);
    }

    @Override
    public void deleteContent(String uuid, String timestamp) {
        stepContentRepository.deleteByStepName(uuid, timestamp);
    }

    @Override
    public void updateStepContents(String uuid, String timestamp, StepContent stepContents) {
        stepContentRepository.updateStepContent(uuid, timestamp, stepContents);
    }

    @Override
    public void updateThumbImg(String uuid, String timestamp, String thumbImg) {
        stepContentRepository.updateThumbImg(uuid, timestamp, thumbImg);
    }
}
