package com.example.demo.jpa.openapi.dto;

import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.openapi.model.parent.OceanQualityParent;
import lombok.Getter;

import java.util.List;

@Getter
public class OceanQualityRequestDto {
    List<OceanQuality> data;
    String memo;
    String title;
}
