package com.example.demo.jpa.openapi.dto;

import com.example.demo.jpa.openapi.model.entity.CityAirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import lombok.Getter;

import java.util.List;

@Getter
public class CityAirQualityRequestDto {
    List<CityAirQuality> data;
    String memo;
    String title;
}
