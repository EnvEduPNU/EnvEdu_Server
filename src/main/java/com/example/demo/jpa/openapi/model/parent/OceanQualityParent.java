package com.example.demo.jpa.openapi.model.parent;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class OceanQualityParent extends Data {
    private String PTNM;
    private LocalDateTime ITEMDATE;
    private String ITEMWMWK;
    private String ITEMWNDEP;
    private String ITEMMDO;
    private String ITEMBOD;
    private String ITEMCOD;
    private String ITEMSS;
    private String ITEMTN;
    private String ITEMTP;
    private String ITEMTOC;
}
