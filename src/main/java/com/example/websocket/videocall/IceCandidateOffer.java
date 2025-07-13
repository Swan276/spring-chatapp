package com.example.websocket.videocall;

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
public class IceCandidateOffer {
    
    private String participantId;
    private Object iceCandidate;
}
