package com.example.demo.jpa.image.controller;

import com.example.demo.jpa.image.annotation.S3DeleteObject;
import com.example.demo.jpa.image.utils.S3PreSignedUrlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Slf4j
public class S3Controller {

    private final S3PreSignedUrlGenerator s3PreSignedUrlGenerator;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketname;

    @Value("${cloud.aws.s3.bucket.folder}")
    private String buckedFolder;

    @Value("${cloud.aws.region.static}")
    private String regionProp;

    //PresignedUrl을 만드는 메서드입니다.
    @GetMapping("/presigned-url")
    public ResponseEntity<Map<String, String>> S3GeneratePreSignedURL(@RequestParam String fileName) {


        log.info("메서드 도착 : " +fileName);

        try {
            String bucketName = bucketname; // S3 버킷 이름
            String region = regionProp; // S3 버킷이 위치한 리전
            String key = buckedFolder + fileName; // S3에 저장될 파일 경로

            // Presigned URL 생성
            String preSignedUrl = s3PreSignedUrlGenerator.getPreSignedUrl(fileName);

            // 이미지 URL 생성
            String imageUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

            // 반환할 맵 생성
            Map<String, String> response = new HashMap<>();
            response.put("preSignedUrl", preSignedUrl);
            response.put("imageUrl", imageUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating Pre-Signed URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error generating Pre-Signed URL"));
        }
    }


    // 이미지 삭제하려면 헤더에 url 설정해줘야함
    @DeleteMapping("/delete")
    @S3DeleteObject
    public ResponseEntity<String> deleteImage(@RequestHeader("X-Previous-Image-URL") String imageUrl) {
        // 실제로 S3 객체를 삭제하는 로직은 AOP에서 처리.
        return ResponseEntity.ok("Image deleted successful");
    }
}
