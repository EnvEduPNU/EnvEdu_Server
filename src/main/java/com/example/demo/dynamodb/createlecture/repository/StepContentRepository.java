package com.example.demo.dynamodb.createlecture.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.demo.dynamodb.createlecture.entity.StepContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StepContentRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public void save(StepContent stepContent) {
        try {
            dynamoDBMapper.save(stepContent);
        } catch (Exception e) {
            log.error("Error saving StepContent: {}", e.getMessage());
            throw e;
        }
    }

    public StepContent findByStepName(String stepName) {
        try {
            return dynamoDBMapper.load(StepContent.class, stepName);
        } catch (Exception e) {
            log.error("Error finding StepContent by stepName: {}", e.getMessage());
            throw e;
        }
    }

    public void deleteByStepName(String uuid, String timestamp) {
        try {
            StepContent stepContent = dynamoDBMapper.load(StepContent.class, uuid);
            if (stepContent != null) {
                log.info("Deleting StepContent: {}", stepContent);
                dynamoDBMapper.delete(stepContent);
            } else {
                log.warn("No StepContent found for UUID: {}, Timestamp: {}", uuid, timestamp);
            }
        } catch (Exception e) {
            log.error("Error deleting StepContent: {}", e.getMessage());
            throw e;
        }
    }

    public List<StepContent> findAll() {
        try {
            return dynamoDBMapper.scan(StepContent.class, new DynamoDBScanExpression());
        } catch (Exception e) {
            log.error("Error finding all StepContents: {}", e.getMessage());
            throw e;
        }
    }

    public StepContent findOne(String uuid) {
        try {
            return dynamoDBMapper.load(StepContent.class, uuid);
        } catch (Exception e) {
            log.error("Error finding StepContent by UUID: {}", e.getMessage());
            throw e;
        }
    }

    public void updateStepContent(String uuid, String timestamp, StepContent updatedContent) {
        try {
            StepContent existingContent = dynamoDBMapper.load(StepContent.class, uuid, timestamp);
            if (existingContent != null) {
                log.info("Updating StepContent: {}", existingContent);
                dynamoDBMapper.save(updatedContent);
            } else {
                log.warn("No StepContent found to update for UUID: {}, Timestamp: {}", uuid, timestamp);
            }
        } catch (Exception e) {
            log.error("Error updating StepContent: {}", e.getMessage());
            throw e;
        }
    }

    public void updateThumbImg(String uuid, String timestamp, String thumbImg) {
        try {
            StepContent existingEntity = dynamoDBMapper.load(StepContent.class, uuid);
            if (existingEntity != null) {
                log.info("Existing StepContent found: {}", existingEntity);
                existingEntity.setThumbImg(thumbImg);
                dynamoDBMapper.save(existingEntity);
                log.info("ThumbImg successfully updated: {}", thumbImg);
            } else {
                log.error("No StepContent found to update ThumbImg for UUID: {}, Timestamp: {}", uuid, timestamp);
                throw new RuntimeException("Entity not found");
            }
        } catch (Exception e) {
            log.error("Error updating ThumbImg: {}", e.getMessage());
            throw e;
        }
    }
}
