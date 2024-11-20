package com.example.demo.jpa.openapi.model.parent;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AirQualityParent extends Data {
    @JsonProperty("stationName")
    private String stationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("ITEMDATE")
    private LocalDate ITEMDATE;

    @JsonProperty("ITEMNO2")
    private String ITEMNO2;

    @JsonProperty("ITEMO3")
    private String ITEMO3;

    @JsonProperty("ITEMPM10")
    private String ITEMPM10;

    @JsonProperty("ITEMPM25")
    private String ITEMPM25;

    @JsonProperty("ITEMSO2VALUE") // JSON 요청의 키와 일치하도록 수정
    private String ITEMSO2VALUE;


}
