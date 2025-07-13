package com.example.websocket.chatroom;

import java.util.Comparator;

import com.example.websocket.chat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomResponse {
    
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private ChatMessage latestChatMessage;
    
    public static ChatRoomResponse fromChatRoom(
        ChatRoom chatRoom,
        ChatMessage latestChatMessage
    ) {
        return ChatRoomResponse
            .builder()
            .id(chatRoom.getId())
            .chatId(chatRoom.getChatId())
            .senderId(chatRoom.getSenderId())
            .recipientId(chatRoom.getRecipientId())
            .latestChatMessage(latestChatMessage)
            .build();
    }
}

class ChatRoomResponseComparator implements Comparator<ChatRoomResponse> {

    @Override
    public int compare(ChatRoomResponse room1, ChatRoomResponse room2) {
        return room2.getLatestChatMessage().getTimestamp().compareTo(room1.getLatestChatMessage().getTimestamp());
    }

}
