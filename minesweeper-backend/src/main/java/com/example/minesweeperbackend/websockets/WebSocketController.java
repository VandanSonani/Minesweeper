package com.example.minesweeperbackend.websockets;

import com.example.minesweeperbackend.gameplay.Gameboard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Map<String, Gameboard> campaignGameBoards = new ConcurrentHashMap<>();
    private final Map<String, Gameboard> practiceGameBoards = new ConcurrentHashMap<>();
    private final Gameboard dailySweepGameboard = new Gameboard(15, 15, 40); // Shared across all players


    @MessageMapping("/minesweeper-websocket")
    public void handleWebSocketMessage(@Payload String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);

        JsonNode modeNode = jsonNode.get("mode");
        JsonNode actionNode = jsonNode.get("action");
        JsonNode sessionIdNode = jsonNode.get("sessionId");

        if (modeNode == null || actionNode == null || sessionIdNode == null) {
            System.out.println("Invalid message payload: " + message);
            return;
        }

        String mode = modeNode.asText();
        String action = actionNode.asText();
        String sessionId = sessionIdNode.asText();

        switch (mode) {
            case "campaign":
                handleCampaignMode(sessionId, jsonNode, action);
                break;
            case "dailySweep":
                handleDailySweepMode(jsonNode, action);
                break;
            case "practice":
                handlePracticeMode(sessionId, jsonNode, action);
                break;
            default:
                System.out.println("Unknown game mode: " + mode);
        }
    }

    private void handleCampaignMode(String sessionId, JsonNode jsonNode, String action) {
        Gameboard gameboard = campaignGameBoards.computeIfAbsent(sessionId, k -> new Gameboard(15, 15, 40));
        if ("revealCell".equals(action)) {
            int x = jsonNode.get("x").asInt();
            int y = jsonNode.get("y").asInt();
            gameboard.revealCell(x, y);
            gameboard.printGameboard();
            messagingTemplate.convertAndSend("/topic/campaign/" + sessionId, gameboard);
        }
    }

    private void handleDailySweepMode(JsonNode jsonNode, String action) {
        if ("revealCell".equals(action)) {
            int x = jsonNode.get("x").asInt();
            int y = jsonNode.get("y").asInt();
            dailySweepGameboard.revealCell(x, y);
            dailySweepGameboard.printGameboard();
            messagingTemplate.convertAndSend("/topic/dailySweep", dailySweepGameboard);
        }
    }

    private void handlePracticeMode(String sessionId, JsonNode jsonNode, String action) {
        if ("initialize".equals(action)) {
            System.out.println("Initializing gameboard for practice mode: " + sessionId);
            Gameboard gameboard = new Gameboard(15, 15, 40);
            gameboard.placeBombs(Optional.empty());
            practiceGameBoards.put(sessionId, gameboard);
            messagingTemplate.convertAndSend("/topic/practice/" + sessionId, gameboard);
        } else if ("revealCell".equals(action)) {
            Gameboard gameboard = practiceGameBoards.get(sessionId);
            if (gameboard != null) {
                int x = jsonNode.get("x").asInt();
                int y = jsonNode.get("y").asInt();
                gameboard.revealCell(x, y);
                gameboard.printGameboard();
                messagingTemplate.convertAndSend("/topic/practice/" + sessionId, gameboard);
            }
        }
    }
}