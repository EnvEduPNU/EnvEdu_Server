package com.example.demo.jpa.eclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentStepCheckDTO {
    @Getter
    private boolean[] stepCheck;
    private Long studentId;
}
