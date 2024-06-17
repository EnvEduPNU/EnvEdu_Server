package com.example.demo.jpa.socket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ScreenShareController {

    @MessageMapping("/share")
    @SendTo("/topic/screen")
    public String handleScreenShare(String data) {
        return data;
    }
}
