package com.zion.pomodorozion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TimerWebSocketHandler timerWebSocketHandler;

    public WebSocketConfig(TimerWebSocketHandler timerWebSocketHandler) {
        this.timerWebSocketHandler = timerWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(timerWebSocketHandler, "/ws").setAllowedOrigins("*");
    }
}
