package com.example.demo.jpa.eclass.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssginmentStepListDTO {
    private List<String> studentData;
    private String eclassUuid;
}
