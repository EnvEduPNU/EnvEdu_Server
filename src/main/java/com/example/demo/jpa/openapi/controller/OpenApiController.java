package com.example.demo.jpa.openapi.controller;

import com.example.demo.jpa.openapi.dto.*;
import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.openapi.model.parent.OceanQualityParent;
import com.example.demo.jpa.openapi.service.OpenApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenApiController {
    @Value("${spring.open-api.gonggong-data.air-status}")
    private String serviceKeyAirStatus;
    @Value("${spring.open-api.gonggong-data.air-station}")
    private String serviceKeyAirStation;
    private final OpenApiService openApiService;

    // 측정소 목록 가져오기
    @GetMapping("/air-quality/station")
    public ResponseEntity<?> getAirQualityStation(@RequestParam(name = "addr", defaultValue = "부산") String addr,
                                                  @RequestParam(name = "stationName", defaultValue = "") String stationName) throws UnsupportedEncodingException, JsonProcessingException {
        String[] key = {"serviceKey", "returnType", "numOfRows", "pageNo", "addr", "stationName"};
        String[] value = {serviceKeyAirStation, "json", "100", "1", addr, stationName};

        AirQualityStationDTO airQualityStationDTO = new AirQualityStationDTO();
        List<AirQualityStationDTO> airQualityStationDTOS = airQualityStationDTO.convertToAirQualityStation(openApiService.callApi("https://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getMsrstnList?", key, value));

        return new ResponseEntity<>(airQualityStationDTOS, HttpStatus.OK);
    }

    // 특정 측정소 자료 (일간, 1달, 3달) 또는 전체 측정소 자료 (일간, 1달, 3달)
    @GetMapping("/air-quality")
    public ResponseEntity<?>  getAirQuality(@RequestParam(name="location", defaultValue = "부산") String location,
                                            @RequestParam(name = "stationName", defaultValue = "") String stationName,
                                            @RequestParam(name = "dataTerm", defaultValue = "DAILY") String dataTerm,
                                            @RequestParam(name = "pageSize", defaultValue = "100") String pageSize,
                                            @RequestParam(name = "pageNo", defaultValue = "1") String pageNo) throws UnsupportedEncodingException, JsonProcessingException {

        String[] key = {"serviceKey", "returnType", "numOfRows", "pageNo", "sidoName", "ver"};
        String[] value = {serviceKeyAirStatus, "json", pageSize, pageNo, location, "1.0"};
        String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?";

        if(!stationName.isEmpty()){
            key = new String[]{"serviceKey", "returnType", "numOfRows", "pageNo", "stationName", "dataTerm", "ver"};
            value = new String[]{serviceKeyAirStatus, "json", pageSize, pageNo, stationName, dataTerm, "1.0"};
            url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?";
        }

        AirQualityDTO airQualityDTO = new AirQualityDTO();

        String airData = String.valueOf(openApiService.callApi(url, key, value));

        log.info("가져오는 에어 데이터 확인 : " + airData);


        List<AirQualityDTO> airQualityDTOS = airQualityDTO.convertToAirQuality(openApiService.callApi(url, key, value));

        if(!stationName.isEmpty()){
            for(AirQualityDTO single: airQualityDTOS) {
                single.setStationName(stationName);
            }
        }

        return new ResponseEntity<>(airQualityDTOS, HttpStatus.OK);
    }

    @GetMapping("/ocean-quality")
    public ResponseEntity<?> getOceanQuality(@RequestParam(name="year", defaultValue = "2022") String wmyrList,
                                             @RequestParam(name="months", defaultValue = "06") String months,
                                             @RequestParam(name = "pageSize", defaultValue = "50") String pageSize,
                                             @RequestParam(name = "pageNo", defaultValue = "1") String pageNo) throws UnsupportedEncodingException, JsonProcessingException {
        String[] key = {"ServiceKey", "pageNo", "numOfRows", "resultType", "ptNoList", "wmyrList", "wmodList"};
        String[] value = {serviceKeyAirStatus, pageNo, pageSize, "JSON", "", wmyrList, months};

        ResponseEntity<String> stringResponseEntity = openApiService.callApi("https://apis.data.go.kr/1480523/WaterQualityService/getWaterMeasuringListMavg?", key, value);
        OceanQualityDTO oceanQualityDTO = new OceanQualityDTO();

        List<OceanQualityDTO> convertOceant = oceanQualityDTO.convertToOceanQuality(stringResponseEntity);

        log.info("받아온것 확인 ConvertDTO: " + convertOceant);
        log.info("받아온것 확인 DTO: " + stringResponseEntity.getBody());


        return new ResponseEntity<>(convertOceant, HttpStatus.OK);
    }

    @PostMapping("/air-quality")
    public ResponseEntity<?> setAirQuality(@RequestBody AirQualityRequestDto airQualityRequestDto, HttpServletRequest request){

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        List<AirQuality> respAir = openApiService.saveAirQuality(airQualityRequestDto.getData(), userName, airQualityRequestDto.getMemo(), airQualityRequestDto.getTitle());

        return ResponseEntity.of(Optional.of(respAir));

    }

    @PostMapping("/ocean-quality")
    public ResponseEntity<?> setOceanQuality(@RequestBody OceanQualityRequestDto oceanQualityRequestDto, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        log.info("Username: {}", userName);

        log.info("data : {}", oceanQualityRequestDto.getData());

        List<OceanQuality> respOcean = openApiService.saveOceanQuality(
                oceanQualityRequestDto.getData(),
                userName,
                oceanQualityRequestDto.getTitle(),
                oceanQualityRequestDto.getMemo()
        );

        if (respOcean == null || respOcean.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(respOcean);

    }


    @DeleteMapping("/air-quality/mine/{airQualityId}")
    public ResponseEntity<?> deleteAirQuality(@PathVariable long airQualityId){


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/ocean-quality/mine/{oceanQualityId}")
    public ResponseEntity<?> deleteOceanQuality(@PathVariable long oceanQualityId){


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/air-quality/mine/chunk")
    public ResponseEntity<?> getMyAirQualityChunked(@RequestParam UUID dataUUID, HttpServletRequest request) {

        String userName = request.getHeader("userName");
        log.info("Username : {}", userName);
        log.info("dataUUID : {}", dataUUID);

        // 서비스 호출
        List<AirQuality> checkAirQuality = openApiService.findMyAirQualityChunked(dataUUID, userName);

        // Memo 리스트 추출
        List<String> memoList = checkAirQuality.stream()
                .map(AirQuality::getMemo)
                .collect(Collectors.toList());
        log.info("Memo List: {}", memoList);

        // Title 리스트 추출
        List<String> titleList = checkAirQuality.stream()
                .map(AirQuality::getTitle)
                .collect(Collectors.toList());
        log.info("Title List: {}", titleList);

        // AirQuality 객체를 Map으로 변환
        List<Map<String, Object>> response = checkAirQuality.stream()
                .map(airQuality -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("stationName", airQuality.getStationName());
                    map.put("ITEMDATE", airQuality.getITEMDATE());
                    map.put("ITEMNO2", airQuality.getITEMN02());
                    map.put("ITEMO3", airQuality.getITEM03());
                    map.put("ITEMPM10", airQuality.getITEMPM10());
                    map.put("ITEMPM25", airQuality.getITEMPM25());
                    map.put("ITEMSO2VALUE", airQuality.getITEMS02VALUE());
                    return map;
                })
                .collect(Collectors.toList());

        // 최종 응답 구조 생성
        Map<String, Object> responseFinal = new HashMap<>();
        responseFinal.put("data", response);
        responseFinal.put("memo", memoList.isEmpty() ? null : memoList.get(0)); // 첫 번째 Memo 사용
        responseFinal.put("title", titleList.isEmpty() ? null : titleList.get(0)); // 첫 번째 Title 사용

        return new ResponseEntity<>(responseFinal, HttpStatus.OK);
    }


    @GetMapping("/ocean-quality/mine/chunk")
    public ResponseEntity<?> getMyOceanQualityChunked(@RequestParam UUID dataUUID, HttpServletRequest request){

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);
        log.info("dataUUID : " + dataUUID);

        List<OceanQuality> checkOcean = openApiService.findMyOceanQualityChunked(dataUUID, userName);

        List<String> memoList = checkOcean.stream()
                .map(OceanQuality::getMemo) // Data 클래스의 memo 필드
                .collect(Collectors.toList()); // Collectors.toList() 사용

        log.info("Memo List: " + memoList);


        List<String> titleList = checkOcean.stream()
                .map(OceanQuality::getTitle) // Data 클래스의 memo 필드
                .collect(Collectors.toList()); // Collectors.toList() 사용

        log.info("titleList: " + titleList);

        // OceanQuality를 OceanQualityResponseDto로 매핑
        List<OceanQualityResponseDto> response = checkOcean.stream()
                .map(ocean -> new OceanQualityResponseDto(
                        ocean.getPTNM(),
                        ocean.getITEMDATE(),
                        ocean.getITEMWMWK(),
                        ocean.getITEMWNDEP(),
                        ocean.getITEMDO(),
                        ocean.getITEMBOD(),
                        ocean.getITEMCOD(),
                        ocean.getITEMSS(),
                        ocean.getITEMTN(),
                        ocean.getITEMTP(),
                        ocean.getITEMTOC(),
                        ocean.getITEMTEMP()
                ))
                .collect(Collectors.toList()); // Collectors.toList() 사용

        // 최종 DTO 생성
        List<OceanQualityResponseFinalDto> responseFinal = List.of(
                new OceanQualityResponseFinalDto(response, memoList.get(0), titleList.get(0))
        );

        return new ResponseEntity<>(responseFinal, HttpStatus.OK);
    }

    // 연.월.일 시간:분
    @GetMapping("/air-quality/mine")
    public ResponseEntity<?> getMyAirQuality(@RequestParam String username,
                                             @RequestParam(name="startDateTime", defaultValue = "") String startDateTime,
                                             @RequestParam(name="endDateTime", defaultValue = "") String endDateTime){
        LocalDateTime defaultStart = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime defaultEnd = LocalDateTime.now();

        if (!startDateTime.isEmpty())
            defaultStart = LocalDateTime.parse(startDateTime, DateTimeFormatter.RFC_1123_DATE_TIME);
        if (!endDateTime.isEmpty())
            defaultEnd = LocalDateTime.parse(endDateTime, DateTimeFormatter.RFC_1123_DATE_TIME);

        return new ResponseEntity<>(openApiService.findMyAirQuality(username, defaultStart, defaultEnd), HttpStatus.OK);
    }

    // 연+월
    @GetMapping("/ocean-quality/mine")
    public ResponseEntity<?> getMyOceanQuality(@RequestParam String username,
                                               @RequestParam(name="startYear", defaultValue = "1900") String startYear,
                                               @RequestParam(name="startMonth", defaultValue = "01") String startMonth,
                                               @RequestParam(name="endYear", defaultValue = "") String endYear,
                                               @RequestParam(name="endMonth", defaultValue = "") String endMonth){
        YearMonth now = YearMonth.now();
        if (endYear.isEmpty())
            endYear = String.valueOf(now.getYear());
        if (endMonth.isEmpty())
            endMonth = String.valueOf(now.getMonthValue());

        return new ResponseEntity<>(openApiService.findMyOceanQuality(username), HttpStatus.OK);
    }

    @ExceptionHandler(JsonProcessingException.class)
    private ResponseEntity<?> jsonProcessingException(JsonProcessingException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    private ResponseEntity<?> unsupportedEncodingExceptionHandler(UnsupportedEncodingException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    private ResponseEntity<?> jsonProcessingExceptionHandler(JsonProcessingException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
