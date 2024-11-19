package com.example.demo.jpa.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OceanQualityResponseDto {

    @JsonProperty("PTNM")
    private String PTNM;

    @JsonProperty("ITEMDATE")
    private LocalDate ITEMDATE;

    @JsonProperty("ITEMWMWK")
    private String ITEMWMWK;

    @JsonProperty("ITEMWNDEP")
    private String ITEMWNDEP;

    @JsonProperty("ITEMDO")
    private String ITEMDO;

    @JsonProperty("ITEMBOD")
    private String ITEMBOD;

    @JsonProperty("ITEMCOD")
    private String ITEMCOD;

    @JsonProperty("ITEMSS")
    private String ITEMSS;

    @JsonProperty("ITEMTN")
    private String ITEMTN;

    @JsonProperty("ITEMTP")
    private String ITEMTP;

    @JsonProperty("ITEMTOC")
    private String ITEMTOC;

    @JsonProperty("ITEMTEMP")
    private String ITEMTEMP;
}
