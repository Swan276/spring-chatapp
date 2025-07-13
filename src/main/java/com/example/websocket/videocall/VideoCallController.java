package com.example.websocket.videocall;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.websocket.user.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Controller
@RequiredArgsConstructor
public class VideoCallController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @MessageMapping("/makeCall")
    public void forwardCall(
        @Payload CallOffer callOffer,
        @Header("userId") String userId
    ) {
        var caller = userService.getUserById(userId);
        messagingTemplate.convertAndSendToUser(
            callOffer.getParticipantId(), 
            "/newCall",
            CallOffer.builder()
                .participantId(userId)
                .participantName(caller.getFullName())
                .sdpOffer(callOffer.getSdpOffer())
                .build()
        );
    }

    @MessageMapping("/answerCall")
    public void forwardAnswer(
        @Payload CallAnswer callAnswer,
        @Header("userId") String userId
    ) {
        messagingTemplate.convertAndSendToUser(
            callAnswer.getParticipantId(), 
            "/callAnswered",
            CallAnswer.builder()
                .participantId(userId)
                .sdpAnswer(callAnswer.getSdpAnswer())
                .build()
        );
    }

    @MessageMapping("/rejectCall")
    public void forwardReject(
        @Payload CallAnswer callAnswer,
        @Header("userId") String userId
    ) {
        messagingTemplate.convertAndSendToUser(
            callAnswer.getParticipantId(), 
            "/callRejected",
            CallAnswer.builder()
                .participantId(userId)
                .build()
        );
    }

    @MessageMapping("/IceCandidate")
    public void forwardIceCandidate(
        @Payload IceCandidateOffer iceCandidateOffer,
        @Header("userId") String userId
    ) {
        messagingTemplate.convertAndSendToUser(
            iceCandidateOffer.getParticipantId(), 
            "/IceCandidate",
            IceCandidateOffer.builder()
                .participantId(userId)
                .iceCandidate(iceCandidateOffer.getIceCandidate())
                .build()
        );
    }

    @MessageMapping("/endCall")
    public void forwardEndCall(
        @Payload CallEnd callEnd,
        @Header("userId") String userId
    ) {
        messagingTemplate.convertAndSendToUser(
            callEnd.getParticipantId(), 
            "/callEnded",
            CallEnd.builder()
                .participantId(userId)
                .build()
        );
    }
}
