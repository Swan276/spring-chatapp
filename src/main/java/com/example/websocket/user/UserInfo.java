package com.example.websocket.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    
    private String username;
    private String fullName;    
    private Status status;

    public static UserInfo fromUser(User user) {
        return UserInfo
            .builder()
            .username(user.getUsername())
            .fullName(user.getFullName())
            .status(user.getStatus())
            .build();
    }
}
