package com.example.demo.jpa.openapi.repository;

import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.CityAirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.openapi.model.parent.OceanQualityParent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OpenApiRepository {
    AirQuality findAirQualityById(Long id);
    OceanQuality findOceanQualityById(Long id);
    List<AirQuality> findAirQualityAllByDataUuid(UUID uuid);
    List<AirQuality> findAirQualityAllByUserId(Long id);
    List<OceanQuality> findOceanQualityAllByDataUuid(UUID uuid);
    List<CityAirQuality> findCityAirQualityAllByDataUuid(UUID uuid);
    List<OceanQuality> findOceanQualityAllByUserId(Long id);
    boolean saveAirQuality(List<AirQuality> airQualities);
    boolean saveOceanQuality(List<OceanQuality> oceanQualities);
    boolean saveCityAirQuality(List<CityAirQuality> cityAirQualities);


    // todo: 여기 뜯어 고쳐야됨
    List<AirQuality> findAllByDataTimeBetween(LocalDateTime start, LocalDateTime end);

}
