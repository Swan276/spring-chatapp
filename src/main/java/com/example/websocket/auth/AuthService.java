package com.example.websocket.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.websocket.config.JwtService;
import com.example.websocket.user.Status;
import com.example.websocket.user.User;
import com.example.websocket.user.UserInfo;
import com.example.websocket.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .username(request.getUsername())
            .fullName(request.getFullName())
            .status(Status.ONLINE)
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
            .builder()
            .user(UserInfo.fromUser(user))
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );
        var user = userRepository.findById(request.getUsername())
            .orElseThrow();
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
            .builder()
            .user(UserInfo.fromUser(user))
            .token(jwtToken)
            .build();
    }
}
