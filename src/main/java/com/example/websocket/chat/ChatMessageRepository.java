package com.example.websocket.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Optional<List<ChatMessage>> findByChatIdOrderByTimestampDesc(String chatId);
}
