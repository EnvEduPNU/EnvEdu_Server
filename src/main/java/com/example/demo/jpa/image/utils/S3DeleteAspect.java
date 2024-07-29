package com.example.demo.jpa.image.utils;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class S3DeleteAspect {

    private final S3ObjectDeleter s3ObjectDeleter;

    public S3DeleteAspect(S3ObjectDeleter s3ObjectDeleter) {
        this.s3ObjectDeleter = s3ObjectDeleter;
    }

    private static final String S3_DELETE_OBJECT_HEADER = "X-Previous-Image-URL";

    @Pointcut("@annotation(com.example.demo.jpa.image.annotation.S3DeleteObject)")
    public void s3DeleteObjectPointCut() {
    }

    @After("s3DeleteObjectPointCut()")
    public void afterS3DeleteObject(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return; // 현재 요청이 없는 경우
        }

        HttpServletRequest request = attributes.getRequest();
        String targetObjectUrl = request.getHeader(S3_DELETE_OBJECT_HEADER);

        if (Objects.nonNull(targetObjectUrl) && !targetObjectUrl.isEmpty()) {
            s3ObjectDeleter.deleteObjectByObjectUrl(targetObjectUrl);
            log.debug("Delete S3 Object {}", targetObjectUrl);
        }
    }

}
