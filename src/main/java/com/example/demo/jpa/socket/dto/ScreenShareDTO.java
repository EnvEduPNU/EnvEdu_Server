package com.example.demo.jpa.socket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenShareDTO {

        private String type;
        private String sdp;
        private String candidate;
        private String from;
        private String to;

}
