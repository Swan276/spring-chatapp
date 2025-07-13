package com.example.websocket.chatroom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.websocket.chat.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatRoomResponse> getChatRoomListByUser(String userId) {
        var chatRoomResponseList = new ArrayList<ChatRoomResponse>();
        var chatRoomList = chatRoomRepository.findAllBySenderId(userId);
        if(chatRoomList.isPresent()) {
            for (ChatRoom chatRoom : chatRoomList.get()) {
                var chatRoomResponse = buildChatRoomResponse(chatRoom);
                if (chatRoomResponse != null) {
                    chatRoomResponseList.add(chatRoomResponse);
                }
            }
        }

        chatRoomResponseList.sort(new ChatRoomResponseComparator());

        return chatRoomResponseList;
    }
    
    public Optional<String> getChatRoomId(
        String senderId, 
        String recipientId, 
        boolean createNewRoomIfNotExists
    ) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
            .chatId(chatId)
            .senderId(senderId)
            .recipientId(recipientId)
            .build();

        ChatRoom recipientSender = ChatRoom.builder()
            .chatId(chatId)
            .senderId(recipientId)
            .recipientId(senderId)
            .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }

    private ChatRoomResponse buildChatRoomResponse(ChatRoom chatRoom) {
        try {
            var latestChatMessage = chatMessageRepository.findFirstByChatIdOrderByTimestampDesc(chatRoom.getChatId()).get();    
            return ChatRoomResponse.fromChatRoom(
                chatRoom, 
                latestChatMessage
            );
        } catch (Exception e) {
            return null;
        }
    }
}


