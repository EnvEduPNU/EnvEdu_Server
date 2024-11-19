package com.example.demo.jpa.openapi.service;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.jpa.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.jpa.openapi.dto.OpenApiParam;
import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.openapi.model.parent.OceanQualityParent;
import com.example.demo.jpa.openapi.module.OpenApiRequest;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import com.example.demo.jpa.openapi.repository.OpenApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {
    private final OpenApiRequest openApiRequest;
    private final OpenApiRepository openApiRepositoryImpl;
    private final UserRepository userRepository;
    private final DataChunkService dataChunkService;

    public ResponseEntity<String> callApi(String domain, String[] key, String[] value) throws UnsupportedEncodingException {
        return openApiRequest.call(new OpenApiParam.OpenApiParamBuilder()
                .setDomain(domain)
                .setKey(key)
                .setValue(value)
                .build());
    }

    @Transactional
    public boolean deleteAirQuality(long airQualityId){
        return false;
    }

    @Transactional
    public List<AirQuality> saveAirQuality(List<AirQuality> airQualities, String username, String memo, String title) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        for (AirQuality airQuality : airQualities) {
            airQuality.setOwner(user.get());
            airQuality.updateBasicAttribute(uuid, now, memo, title, DataEnumTypes.AIRQUALITY);
        }

        log.info("저장하기 전에 확인 : " + airQualities);

        dataChunkService.saveMyDataCompilation(uuid, DataEnumTypes.AIRQUALITY.name(), user.get(), now, airQualities.size(), memo, title);

        if(openApiRepositoryImpl.saveAirQuality(airQualities)){
            return airQualities;
        }

        throw new NoSuchElementException();
    }

    @Transactional
    public List<OceanQuality> saveOceanQuality(List<OceanQuality> oceanQualities, String username, String memo, String title) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        for (OceanQuality oceanQuality : oceanQualities) {
            oceanQuality.updateBasicAttribute(uuid, now, memo,title, DataEnumTypes.OCEANQUALITY);
        }

        log.info("값 검증 : " +oceanQualities);

        dataChunkService.saveMyDataCompilation(uuid, "OCEANQUALITY", user.get(), now, oceanQualities.size(), title, memo);

        if(openApiRepositoryImpl.saveOceanQuality(oceanQualities)){
            log.info("공공데이터 OceanQuality 저장 완료");
            return oceanQualities;
        }
        throw new NoSuchElementException();
    }

    public List<AirQuality> findMyAirQualityChunked(UUID uuid, String username){
        Optional<User> user = userRepository.findByUsername(username);
        return openApiRepositoryImpl.findAirQualityAllByUserIdAndDataUuid(uuid, user.get().getId());
    }

    public List<OceanQuality> findMyOceanQualityChunked(UUID uuid, String username){
        Optional<User> user = userRepository.findByUsername(username);

        log.info("dataUUID : " + uuid);

        return openApiRepositoryImpl.findOceanQualityAllByDataUuid(uuid);
    }

    public List<AirQuality> findMyAirQuality(String username, LocalDateTime start, LocalDateTime end) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        List<AirQuality> result = openApiRepositoryImpl.findAirQualityAllByUserId(user.get().getId());

        //List<AirQuality> result = openApiRepositoryImpl.findAllByDataTimeBetween(start, end);

        for (AirQuality airQuality : result) {
            airQuality.setOwner(null);
        }
        return result;
    }

    public List<OceanQuality> findMyOceanQuality(String username) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        List<OceanQuality> result = openApiRepositoryImpl.findOceanQualityAllByUserId(user.get().getId());

        for (OceanQuality oceanQuality : result) {
            oceanQuality.setOwner(null);
        }
        return result;
    }
}
