package com.example.demo.jpa.socket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import java.time.Duration;

@RestController
public class EventStreamController {

    // SSE를 통해 클라이언트로 데이터스트림을 전송
    @GetMapping(value = "/ws", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "data: Event " + sequence + "\n\n");
    }
}

