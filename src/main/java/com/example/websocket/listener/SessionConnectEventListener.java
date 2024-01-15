package com.example.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.example.websocket.user.UserService;
import com.example.websocket.utils.WebSocketUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionConnectEventListener extends WebSocketUtils implements ApplicationListener<SessionConnectEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) { 
        System.out.println("Connecting "+ event.getMessage());
        var headers = event.getMessage().getHeaders();
        var userId = super.getNativeHeader(headers, "userId");
        var sessionId = super.getSessionId(headers);
        if (userId != null && sessionId != null) {
            userService.connect(userId, sessionId);
        }
    }
}
