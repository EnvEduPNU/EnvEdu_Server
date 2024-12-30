package com.example.demo.dynamodb.createlecture.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.demo.dynamodb.createlecture.entity.ReportStepContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class ReportStepContentRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public ReportStepContentRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void save(ReportStepContent reportStepContent) {
        try {
            dynamoDBMapper.save(reportStepContent);
        } catch (Exception e) {
            log.error("Error saving ReportStepContent: {}", e.getMessage());
            throw e;
        }
    }

    public ReportStepContent findByStepName(String stepName) {
        try {
            return dynamoDBMapper.load(ReportStepContent.class, stepName);
        } catch (Exception e) {
            log.error("Error finding ReportStepContent by stepName: {}", e.getMessage());
            throw e;
        }
    }

    public List<ReportStepContent> getStepContentByUuid(List<String> uuids) {
        List<ReportStepContent> resultList = new ArrayList<>();

        for (String uuid : uuids) {
            ReportStepContent partitionKey = new ReportStepContent();
            partitionKey.setUuid(uuid);

            DynamoDBQueryExpression<ReportStepContent> queryExpression = new DynamoDBQueryExpression<ReportStepContent>()
                    .withHashKeyValues(partitionKey);

            List<ReportStepContent> queryResult = dynamoDBMapper.query(ReportStepContent.class, queryExpression);
            resultList.addAll(queryResult);
        }

        if (resultList.isEmpty()) {
            log.warn("No results found for the provided UUIDs.");
        }

        log.info("레포지토리에서 UUID에 해당하는 ReportStepContent 항목 수: {}", resultList.size());
        return resultList;
    }

    public void deleteByStepName(String uuid, String timestamp) {
        try {
            ReportStepContent reportStepContent = dynamoDBMapper.load(ReportStepContent.class, uuid, timestamp);

            if (reportStepContent != null) {
                log.info("Deleting ReportStepContent: {}", reportStepContent);
                dynamoDBMapper.delete(reportStepContent);
            } else {
                log.warn("No ReportStepContent found for UUID: {}, Timestamp: {}", uuid, timestamp);
            }
        } catch (Exception e) {
            log.error("Error deleting ReportStepContent: {}", e.getMessage());
            throw e;
        }
    }

    public List<ReportStepContent> findAll() {
        try {
            return dynamoDBMapper.scan(ReportStepContent.class, new DynamoDBScanExpression());
        } catch (Exception e) {
            log.error("Error retrieving all ReportStepContent: {}", e.getMessage());
            throw e;
        }
    }

    public void updateStepContent(String uuid, String timestamp, ReportStepContent updatedReportStepContent) {
        try {
            ReportStepContent existingReportStepContent = dynamoDBMapper.load(ReportStepContent.class, uuid);

            if (existingReportStepContent == null) {
                log.warn("No existing ReportStepContent found. Saving new content.");
                dynamoDBMapper.save(updatedReportStepContent);
                return;
            }

            List<ReportStepContent.ContentWrapper> existingContents = existingReportStepContent.getContents();
            List<ReportStepContent.ContentWrapper> updatedContents = updatedReportStepContent.getContents();

            for (ReportStepContent.ContentWrapper updatedContentWrapper : updatedContents) {
                boolean found = false;
                for (int i = 0; i < existingContents.size(); i++) {
                    ReportStepContent.ContentWrapper existingContentWrapper = existingContents.get(i);
                    if (existingContentWrapper.getStepNum() == updatedContentWrapper.getStepNum()) {
                        existingContents.set(i, updatedContentWrapper);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    existingContents.add(updatedContentWrapper);
                }
            }

            existingReportStepContent.setContents(existingContents);
            dynamoDBMapper.save(existingReportStepContent);

        } catch (Exception e) {
            log.error("Error updating ReportStepContent: {}", e.getMessage());
            throw e;
        }
    }
}
