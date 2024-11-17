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
    @JsonProperty("PTNM")
    private String PTNM;

    @JsonProperty("ITEMDATE")
    private LocalDateTime ITEMDATE;

    @JsonProperty("ITEMWMWK")
    private String ITEMWMWK;

    @JsonProperty("ITEMWNDEP")
    private String ITEMWNDEP;

    @JsonProperty("ITEMMDO")
    private String ITEMMDO;

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
}
