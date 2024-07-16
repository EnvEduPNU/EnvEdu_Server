package com.example.demo.jpa.image.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
public class S3ObjectDeleter {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    public S3ObjectDeleter(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }


     //object 의 URL 을 통해서 해당 S3 객체를 삭제하는 메서드 입니다.
    public void deleteObjectByObjectUrl(String objectUrl) {
        String[] parts = objectUrl.split("/");
        String objectKeyBeforeUrlDecode =
                String.join("/", Arrays.copyOfRange(parts, 3, parts.length));

        String urlDecodedObjectKey = getUrlDecodedObjectKey(objectKeyBeforeUrlDecode);
        deleteObject(urlDecodedObjectKey);
    }


    //Object Key를 통해서 S3의 버킷에 존재하는 객체를 삭제하는 메서드 입니다.
    private void deleteObject(String objectKey) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
        } catch (Exception e) {
            log.error("S3 Delete Object Error {}", e.toString());
        }
    }


    //디코딩이 되지 않은 S3 Object URL 을 통해서 얻어온 Object Key 값을 Decode 하는 메서드 입니다.
    private String getUrlDecodedObjectKey(String objectKeyBeforeDecoded) {
        return URLDecoder.decode(objectKeyBeforeDecoded, StandardCharsets.UTF_8);
    }
}
