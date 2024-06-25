package com.example.demo.jpa.socket.util;

import com.example.demo.jpa.socket.dto.StudentChangeDTO;
import com.example.demo.jpa.socket.model.entity.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * 선생님 화면 공유 페이지에서 학생이 들어와서 db에 sessionId가 추가되거나 학생이 나가서 sessionId가 삭제되면 소켓 통신을 통해서 알려주는 메서드입니다.
 * @author 부산대 과학교육연구소 연구보조원 김선규
 */
@Component
@Slf4j
public class TableChangeListener implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static final ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper instance


    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        TableChangeListener.applicationContext = applicationContext;
    }

    @PostPersist
    public void onPostPersist(Session entity) {
        log.info("학생 입장 : " + entity.getSessionId());
        sendMessage(entity.getSessionId(), "입장");
    }

    @PostUpdate
    public void onPostUpdate(Session entity) {
        sendMessage(entity.getSessionId(), "업데이트");
    }

    @PostRemove
    public void onPostRemove(Session entity) {
        log.info("학생 퇴장 : " + entity.getSessionId());
        sendMessage(entity.getSessionId(), "퇴장");
    }

    private static void sendMessage(String sessionId, String action) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext not initialized");
        }
        try {
            SimpMessagingTemplate template = applicationContext.getBean(SimpMessagingTemplate.class);
            String jsonMessage = objectMapper.writeValueAsString(new StudentChangeDTO(sessionId, action));
            template.convertAndSend("/topic/changes", jsonMessage);
        } catch (Exception e) {
            log.error("Error sending websocket message", e);
        }
    }
}