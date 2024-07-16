package com.example.demo.jpa.image.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class SeoulDateTimeProvider {

    //서울의 현재 시간을 반환해주는 메서드입니다.
    public Date getSeoulDateTime() {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime seoulDateTime = ZonedDateTime.now(seoulZoneId);
        Instant instant = seoulDateTime.toInstant();
        return Date.from(instant);
    }

}
