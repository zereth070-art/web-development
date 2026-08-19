package com.zion.pomodorozion;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import tools.jackson.databind.ObjectMapper;

@Component
public class TimerBroadcaster {

    private final TimerService timerService;
    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public TimerBroadcaster(TimerService timerService, ObjectMapper objectMapper) {
        this.timerService = timerService;
        this.objectMapper = objectMapper;
    }

    public void addSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session.getId());
    }

    public void sendTo(WebSocketSession session, TimerState state) {
        try {
            WebSocketSession safe = new ConcurrentWebSocketSessionDecorator(session, 1024, 5000);
            safe.sendMessage(new TextMessage(objectMapper.writeValueAsString(state)));
        } catch (IOException e) {
            removeSession(session);
        }
    }

    public void broadcast(TimerState state) {
        for (WebSocketSession session : sessions.values()) {
            sendTo(session, state);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {
        if (!sessions.isEmpty()) {
            broadcast(timerService.getState());
        }
    }
}
