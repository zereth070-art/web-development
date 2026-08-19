package com.zion.pomodorozion;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TimerWebSocketHandler extends TextWebSocketHandler {

    private final TimerBroadcaster broadcaster;
    private final TimerService timerService;

    public TimerWebSocketHandler(TimerBroadcaster broadcaster, TimerService timerService) {
        this.broadcaster = broadcaster;
        this.timerService = timerService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        broadcaster.addSession(session);
        broadcaster.sendTo(session, timerService.getState());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        broadcaster.removeSession(session);
    }
}
