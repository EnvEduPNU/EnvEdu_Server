package com.example.demo.jpa.image.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//S3 Bucket 에서 객체 삭제 AOP의 PointCut 역할을 하는 Annotation입니다.
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface S3DeleteObject {
}
