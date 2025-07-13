package com.example.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.websocket.user.UserService;
import com.example.websocket.utils.WebSocketUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionDisconnectEventListener extends WebSocketUtils implements ApplicationListener<SessionDisconnectEvent> {

    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) { 
        System.out.println("Disconnecting "+ event.getMessage());
        String sessionId = super.getSessionId(event.getMessage().getHeaders());
        if(sessionId != null) {
            var user = userService.disconnectUserBySession(sessionId);
            if (user != null) {
                messagingTemplate.convertAndSend("/public", user);
            }
        }
    }

}
