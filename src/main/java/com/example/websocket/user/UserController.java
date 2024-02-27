package com.example.websocket.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> findConnectedUsers() {
        return ResponseEntity.ok(service.findConnectedUsers());
    }   
}
