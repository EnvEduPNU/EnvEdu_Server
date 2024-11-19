package com.example.demo.jpa.openapi.dto;

import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OceanQualityResponseFinalDto {
    List<OceanQualityResponseDto> data;
    String memo;
    String title;

    public OceanQualityResponseFinalDto(List<OceanQualityResponseDto> response, String memo, String title) {
        this.data = response;
        this.memo = memo;
        this.title = title;
    }
}
