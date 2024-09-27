package com.example.demo.jpa.socket.controller;

import com.example.demo.jpa.seed.model.Seed;
import com.example.demo.jpa.socket.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate template;
    private final SessionService sessionService;

    /**
     * 기기에서 전송하는 메세지를 받는 controller
     * 기기에서 전송하는 데이터에는 날짜 정보가 없음 -> 여기서 날짜 정보를 추가해 프론트로 전송
     */
    @MessageMapping("/device")
    private void fromESP2Client(@Payload Seed seed) {

        log.info("device 전달 완료");
//        log.info("seed.getMac = " + seed.getMac() + ", seed.getCo2 = " + seed.getCo2() + ", seed.getHum = " + seed.getHum() + ", seed.getDust = " + seed.getDust());
//        String username = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap().get(JwtUtil.claimUsername).toString();
//        seed.updateUsername(username);

        log.info("device 현재 시간 : " +LocalDateTime.now(ZoneId.of("Asia/Seoul")) );
        log.info("seed.getMac : " +seed.getMac() );
        log.info("seed : " +seed );


        template.convertAndSend("/topic/user/" + seed.getMac(), seed);
    }

    @MessageMapping("/switch")
    private void fromEClassClient(@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pageValue = null;
        String stepCountValue = null;
        String lectureDataUuid = null;

        try {
            // JSON 메시지를 파싱하여 값들을 가져옵니다.
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            pageValue = rootNode.path("page").asText();
            stepCountValue = rootNode.path("stepCount").asText();
            lectureDataUuid = rootNode.path("lectureDataUuid").asText();


            // JSON 객체를 만들어 그대로 로그에 출력
            ObjectNode payloadNode = objectMapper.createObjectNode();
            payloadNode.put("page", pageValue);
            payloadNode.put("stepCount", stepCountValue);
            payloadNode.put("lectureDataUuid", lectureDataUuid);


            log.info("device 전달 완료 : {}", payloadNode.toString());

            // JSON 객체를 문자열로 변환하여 전송
            String jsonPayload = objectMapper.writeValueAsString(payloadNode);
            template.convertAndSend("/topic/switchPage", jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }


    @MessageMapping("/screen-share-status")
    private void fromEClassScreenShare(@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pageValue = null;


        try {
            // JSON 메시지를 파싱하여 값들을 가져옵니다.
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            pageValue = rootNode.path("screenStatus").asText();



            // JSON 객체를 만들어 그대로 로그에 출력
            ObjectNode payloadNode = objectMapper.createObjectNode();
            payloadNode.put("screenStatus", pageValue);


            log.info("화면 공유 상태 : {}", payloadNode.toString());

            // JSON 객체를 문자열로 변환하여 전송
            String jsonPayload = objectMapper.writeValueAsString(payloadNode);
            template.convertAndSend("/topic/screen-share-status", jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }

    @MessageMapping("/assginment-status")
    private void fromEClassAssginmentCheck(@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pageValue = null;
        String sessionId = null;
        Boolean assginmentShared = false;
        String timestamp = null;

        try {
            // JSON 메시지를 파싱하여 값들을 가져옵니다.
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            pageValue = rootNode.path("assginmentStatus").asText();
            sessionId = rootNode.path("sessionId").asText();
            assginmentShared = rootNode.path("assginmentShared").asBoolean();
            timestamp = rootNode.path("timestamp").asText();



            // JSON 객체를 만들어 그대로 로그에 출력
            ObjectNode payloadNode = objectMapper.createObjectNode();
            payloadNode.put("assginmentStatus", pageValue);
            payloadNode.put("sessionId", sessionId);
            payloadNode.put("assginmentShared", assginmentShared);
            payloadNode.put("timestamp", timestamp);



            log.info("과제 공유 상태 : {}", payloadNode.toString());

            // JSON 객체를 문자열로 변환하여 전송
            String jsonPayload = objectMapper.writeValueAsString(payloadNode);
            template.convertAndSend("/topic/assginment-status", jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }

    @MessageMapping("/student-entered")
    private void fromEClassStudenetCheck(@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        boolean screenShared = false;
        String sessionId = "";

        log.info("학생 입장 부분 소켓");

        try {
            // JSON 메시지를 파싱하여 값들을 가져옵니다.
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            screenShared = rootNode.path("screenShared").asBoolean();
            sessionId = rootNode.path("sessionId").asText();

            // 브라우저 창 강제 종료를 대비 false 일시 세션 제거
//            if(!entered){
//                sessionService.deleteSession(sessionId);
//            }

            // JSON 객체를 만들어 그대로 로그에 출력
            ObjectNode payloadNode = objectMapper.createObjectNode();
            payloadNode.put("screenShared", screenShared);
            payloadNode.put("sessionId", sessionId);


            log.info("학생 입장 상태 : {}", payloadNode.toString());

            // JSON 객체를 문자열로 변환하여 전송
            String jsonPayload = objectMapper.writeValueAsString(payloadNode);
            template.convertAndSend("/topic/student-entered", jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }

    @MessageMapping("/screen")
    private void fromEClassScreenShareFlag (@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        boolean screenShared = false;
        String sessionId = "";

        log.info("화면 공유 부분 소켓 ");

        try {
            // JSON 메시지를 파싱하여 값들을 가져옵니다.
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            screenShared = rootNode.path("screenShared").asBoolean();
            sessionId = rootNode.path("sessionId").asText();


            // JSON 객체를 만들어 그대로 로그에 출력
            ObjectNode payloadNode = objectMapper.createObjectNode();
            payloadNode.put("screenShared", screenShared);
            payloadNode.put("sessionId", sessionId);

            log.info("화면 공유 상태 : {}", payloadNode.toString());

            // JSON 객체를 문자열로 변환하여 전송
            String jsonPayload = objectMapper.writeValueAsString(payloadNode);
            template.convertAndSend("/topic/screenflag", jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }
    }





}