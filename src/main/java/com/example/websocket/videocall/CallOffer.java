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
public class CallOffer {
    
    private String participantId;
    private String participantName;
    private Object sdpOffer;
}
