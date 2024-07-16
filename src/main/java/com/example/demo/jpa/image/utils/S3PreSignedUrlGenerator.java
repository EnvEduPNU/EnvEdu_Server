package com.example.demo.jpa.image.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * S3의 PreSignedUrl 생성을 담당하는 클래스 입니다.
 *
 **/

@Component
@RequiredArgsConstructor
public class S3PreSignedUrlGenerator {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.s3.bucket.folder}")
    private String buckedFolder;

    private static final int DEFAULT_EXPIRATION_TIME = 5 * 60 * 1000;

     //PreSignedURL 을 가져오는 메서드 입니다.
    public String getPreSignedUrl(String fileName) {

        fileName = buckedFolder.concat(generateUUIDAppendedFileName(fileName));

        URL presignedUrl = amazonS3Client.generatePresignedUrl(
                getGeneratePreSignedUrlRequest(bucketName, fileName)
        );

        return presignedUrl.toString();
    }

    /**
     * S3 Bucket에 해당 파일에 대한 PreSignedURL 을 얻는 Request를 만드는 메서드 입니다.
     * 권한은 파일 업로드만 설정되어 있습니다.
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucketName,
                                                                       String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpirationTime());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }


     //PreSignedURL의 만료시간을 지정합니다.
    private Date getPreSignedUrlExpirationTime() {
        return new Date(System.currentTimeMillis() + DEFAULT_EXPIRATION_TIME);
    }



     //UUID 를 생성해서 파일이름 앞에 붙여주는 메서드 입니다.
    private String generateUUIDAppendedFileName(String fileName) {
        return UUID.randomUUID().toString().concat("_" + fileName);
    }

}