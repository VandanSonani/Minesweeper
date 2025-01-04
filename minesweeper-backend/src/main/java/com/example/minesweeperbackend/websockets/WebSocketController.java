package com.example.minesweeperbackend.websockets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
