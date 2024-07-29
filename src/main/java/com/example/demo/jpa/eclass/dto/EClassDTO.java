package com.example.demo.jpa.eclass.dto;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

@Data
public class EClassDTO {

    @JsonProperty("eClassUuid")
    private String eClassUuid;

    @JsonProperty("lectureDataUuid")
    private String lectureDataUuid;

    @JsonProperty("lectureDataName")
    private String lectureDataName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("lectureName")
    private String lectureName;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("eClassAssginSubmitNum")
    private int eClassAssginSubmitNum;
}
