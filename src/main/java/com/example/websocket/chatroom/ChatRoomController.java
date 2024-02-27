package com.example.websocket.chatroom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    
    private final ChatRoomService service;

    @GetMapping("/chatRooms/{userId}")
    public ResponseEntity<List<ChatRoom>> getChatRoomListByUser(
        @PathVariable("userId") String userId
    ) {
        return ResponseEntity.ok(
            service.getChatRoomListByUser(userId)
                .orElse(new ArrayList<ChatRoom>())
        );
    }
}
