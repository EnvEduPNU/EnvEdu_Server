package com.example.demo.jpa.openapi.model.parent;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AirQualityParent extends Data {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("stationName")
    private String stationName;
    @JsonProperty("ITEMDATE")
    private String ITEMDATE;
    @JsonProperty("ITEMN02")
    private String ITEMN02;
    @JsonProperty("ITEM03")
    private String ITEM03;
    @JsonProperty("ITEMPM10")
    private String ITEMPM10;
    @JsonProperty("ITEMPM25")
    private String ITEMPM25;
    @JsonProperty("ITEMS02VALUE")
    private String ITEMS02VALUE;
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
