package com.example.websocket.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.websocket.chatroom.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
            chatMessage.getSenderId(), 
            chatMessage.getRecipientId(), 
            true
        ).orElseThrow(); // you can create your own dedicated exception
        chatMessage.setChatId(chatId);
        return repository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(
            senderId, 
            recipientId, 
            false
        ).orElseThrow();
        return repository.findByChatIdOrderByTimestampDesc(chatId).orElse(new ArrayList<>());
    }
}
