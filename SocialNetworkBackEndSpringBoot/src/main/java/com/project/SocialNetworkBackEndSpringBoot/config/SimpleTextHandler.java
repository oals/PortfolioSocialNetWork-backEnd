package com.project.SocialNetworkBackEndSpringBoot.config;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.project.SocialNetworkBackEndSpringBoot.dto.NotificationDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class SimpleTextHandler extends TextWebSocketHandler {
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("깃 머지 테스트 수정1회 profile에서 수정2");
        System.out.println("Received message123: " + message.getPayload());
        System.out.println("깃 머지 테스트23");
        System.out.println("충돌 테스트 social")
    }
    public void mergeTest(){
        System.out.println("profile머지테스트");
    }

    public void mergeTestScoial(){

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
    }

    public void sendMessageToAll(String sendMemberId, String readMemberId) throws Exception {
        log.info(sessions);

        for (WebSocketSession session : sessions.values()) {
            JsonObject jsonMessage = new JsonObject();
            jsonMessage.addProperty("sendMemberId", sendMemberId);
            jsonMessage.addProperty("readMemberId", readMemberId);

            session.sendMessage(new TextMessage(jsonMessage.toString()));
        }
    }
}
