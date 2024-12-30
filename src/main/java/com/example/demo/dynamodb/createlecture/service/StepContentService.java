package com.example.demo.dynamodb.createlecture.service;

import com.example.demo.dynamodb.createlecture.entity.StepContent;

import java.util.List;

public interface StepContentService {

    void saveStepContents(StepContent stepContents);

    List<StepContent> getAllStepContents();

    StepContent getStepContent(String uuid);

    void deleteContent(String uuid, String timestamp);

    void updateStepContents(String uuid, String timestamp, StepContent stepContents);

    void updateThumbImg(String uuid, String timestamp, String thumbImg);
}
