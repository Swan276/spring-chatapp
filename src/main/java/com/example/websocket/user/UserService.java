package com.example.websocket.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    
    public void setUserOnline(String username) {
        var user = userRepository.findById(username).orElseThrow();
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public User connectUserBySession(String username, String sessionId) {
        var user = userRepository.findById(username).orElseThrow();
        var userSesssion = UserSession.builder()
            .sessionId(sessionId)
            .userId(username)
            .build();
        userSessionRepository.save(userSesssion);
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
        return user;
    }

    public User disconnectUserBySession(String sessionId) {
        var userSession = userSessionRepository.findById(sessionId).orElse(null);
        if(userSession != null) {
            userSessionRepository.delete(userSession);
            var storedUser = userRepository.findById(userSession.getUserId())
                .orElse(null);
            if(storedUser != null) {
                storedUser.setStatus(Status.OFFLINE);
                userRepository.save(storedUser);
                return storedUser;
            }
        }
        return null;
    }

    public List<UserInfo> findConnectedUsers() {
        var users = userRepository.findAllByStatus(Status.ONLINE);
        return users.stream()
            .map(user -> UserInfo.fromUser(user))
            .collect(Collectors.toList());
    }

    public UserInfo getUserById(String userId) {
        var user = userRepository.findById(userId).orElseThrow();
        return UserInfo.fromUser(user);
    }
}
