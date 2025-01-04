package com.example.minesweeperbackend.websockets;
import com.example.minesweeperbackend.gameplay.Gameboard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



@Component
public class WebSocketHandler extends TextWebSocketHandler {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Gameboard gameboard = new Gameboard(15, 15, 40);


    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        String action = jsonNode.get("action").asText();
        if ("revealCell".equals(action)) {
            int x = jsonNode.get("x").asInt();
            int y = jsonNode.get("y").asInt();
            gameboard.revealCell(x, y);
            gameboard.printGameboard();
            // Broadcast the updated gameboard to all clients
            messagingTemplate.convertAndSend("/topic/gameboard", gameboard);
            System.out.println("Revealed cell at x: " + x + " y: " + y);
        }
    }
}