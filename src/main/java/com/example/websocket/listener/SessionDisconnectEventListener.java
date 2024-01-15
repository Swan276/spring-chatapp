package com.example.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.websocket.user.UserService;
import com.example.websocket.utils.WebSocketUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionDisconnectEventListener extends WebSocketUtils implements ApplicationListener<SessionDisconnectEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) { 
        System.out.println("Disconnecting "+ event.getMessage());
        String sessionId = super.getSessionId(event.getMessage().getHeaders());
        if(sessionId != null) {
            userService.disconnectBySession(sessionId);
        }
    }

}
