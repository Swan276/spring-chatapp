package com.example.websocket.utils;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketUtils {

    public String getSessionId(MessageHeaders headers) {
        var sessionId = headers.get(StompHeaderAccessor.SESSION_ID_HEADER);
        if(sessionId != null) {
            return sessionId.toString();
        }
        return null;
    }    

    public String getNativeHeader(MessageHeaders headers, String key) {
        var nativeHeaders = headers.get(StompHeaderAccessor.NATIVE_HEADERS, Map.class);
        var header = (ArrayList<?>) nativeHeaders.get(key);
        if(header != null && !header.isEmpty()) {
            return header.get(0).toString();
        }
        return null;
    }
}
