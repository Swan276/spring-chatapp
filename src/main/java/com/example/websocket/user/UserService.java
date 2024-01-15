package com.example.websocket.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    
    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void connect(String userId, String sessionId) {
        var userSesssion = UserSession.builder()
            .sessionId(sessionId)
            .userId(userId)
            .build();
        userSessionRepository.save(userSesssion);
    }

    public void disconnectByUser(String userId) {
        var userSession = userSessionRepository.findByUserId(userId).orElse(null);
        if(userSession != null) {
            userSessionRepository.delete(userSession);
        }
        var storedUser = userRepository.findById(userId)
                .orElse(null);
        if(storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public void disconnectBySession(String sessionId) {
        var userSession = userSessionRepository.findById(sessionId).orElse(null);
        if(userSession != null) {
            userSessionRepository.delete(userSession);
        }
        var storedUser = userRepository.findById(userSession.getUserId())
                .orElse(null);
        if(storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
