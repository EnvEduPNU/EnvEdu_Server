package com.example.demo.jpa.openapi.model.parent;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CityAirQualityParent extends Data {
    @JsonProperty("ITEMCODE")
    private String ITEMCODE;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("ITEMDATETIME")
    private LocalDate ITEMDATETIME;

    @JsonProperty("ITEMDAEGU")
    private String ITEMDAEGU;

    @JsonProperty("ITEMCHUNGNAM")
    private String ITEMCHUNGNAM;

    @JsonProperty("ITEMINCHEON")
    private String ITEMINCHEON;

    @JsonProperty("ITEMDAEJEON")
    private String ITEMDAEJEON;

    @JsonProperty("ITEMGYONGBUK")
    private String ITEMGYONGBUK;

    @JsonProperty("ITEMSEJONG")
    private String ITEMSEJONG;

    @JsonProperty("ITEMGWANGJU")
    private String ITEMGWANGJU;

    @JsonProperty("ITEMJEONBUK")
    private String ITEMJEONBUK;

    @JsonProperty("ITEMGANGWON")
    private String ITEMGANGWON;

    @JsonProperty("ITEMULSAN")
    private String ITEMULSAN;

    @JsonProperty("ITEMJEONNAM")
    private String ITEMJEONNAM;

    @JsonProperty("ITEMSEOUL")
    private String ITEMSEOUL;

    @JsonProperty("ITEMBUSAN")
    private String ITEMBUSAN;

    @JsonProperty("ITEMJEJU")
    private String ITEMJEJU;

    @JsonProperty("ITEMCHUNGBUK")
    private String ITEMCHUNGBUK;

    @JsonProperty("ITEMGYEONGNAM")
    private String ITEMGYEONGNAM;

    @JsonProperty("ITEMGYEONGGI")
    private String ITEMGYEONGGI;
}
