package com.example.websocket.chatroom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>  {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

    Optional<List<ChatRoom>> findAllBySenderId(String senderId);

}
