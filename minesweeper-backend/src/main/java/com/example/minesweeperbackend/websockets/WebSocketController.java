package com.example.minesweeperbackend.websockets;

import com.example.minesweeperbackend.gameplay.Gameboard;
import com.example.minesweeperbackend.gameplay.gamemodes.DailySweepGameMode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class WebSocketController {

    private final Map<String, Gameboard> campaignGameBoards = new ConcurrentHashMap<>();
    private final Map<String, Gameboard> practiceGameBoards = new ConcurrentHashMap<>();
    private final Queue<String> rankedQueue = new LinkedList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private DailySweepGameMode dailySweepGameMode;
    private final Map<String, Gameboard> rankedGameBoards = new ConcurrentHashMap<>();
    private final Map<String, String> playerPairs = new ConcurrentHashMap<>();

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
            case "ranked":
                handleRankedMode(sessionId, jsonNode, action);
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

    //todo if a high volume of players join the ranked queue, the scheduler will not be able to handle the load
    // should be replaced with a more robust solution

    private void handleRankedMode(String sessionId, JsonNode jsonNode, String action) {
        if ("joinRankedQueue".equals(action)) {
            rankedQueue.add(sessionId);
            System.out.println("Client " + sessionId + " joined the ranked queue");
            messagingTemplate.convertAndSend("/topic/ranked/" + sessionId, "[LOG] client joined the ranked queue");
            if (rankedQueue.size() >= 2) {
                String player1 = rankedQueue.poll();
                String player2 = rankedQueue.poll();
                playerPairs.put(player1, player2);
                playerPairs.put(player2, player1);
                if (!Objects.equals(player1, player2)) {
                    messagingTemplate.convertAndSend("/topic/ranked/" + player1, "Match will be confirmed in 3 seconds");
                    messagingTemplate.convertAndSend("/topic/ranked/" + player2, "Match will be confirmed in 3 seconds");
                    System.out.println("Match found between " + player1 + " and " + player2);
                    scheduler.schedule(() -> {
                        Gameboard gameboard = new Gameboard(15, 15, 40);
                        Gameboard gameboard2 = new Gameboard(15, 15, 40);
                        Integer randomSeed = new Random().nextInt();

                        gameboard.placeBombs(0, 0, Optional.of(randomSeed));
                        gameboard2.placeBombs(0, 0, Optional.of(randomSeed));
                        gameboard.revealCell(0, 0);
                        gameboard2.revealCell(0, 0);
                        rankedGameBoards.put(player1, gameboard);
                        rankedGameBoards.put(player2, gameboard2);
                        messagingTemplate.convertAndSend("/topic/ranked/" + player1, gameboard);
                        messagingTemplate.convertAndSend("/topic/ranked/" + player2, gameboard2);
                    }, 3, TimeUnit.SECONDS);
                } else {
                    log.error("Player 1 and Player 2 are the same. Ignoring the match.");
                }
            }
        }
        if ("leaveRankedQueue".equals(action)) {
            rankedQueue.remove(sessionId);
            rankedGameBoards.remove(sessionId);
            messagingTemplate.convertAndSend("/topic/ranked/" + sessionId, "[LOG] client left the ranked queue");
            System.out.println("Client " + sessionId + " left the ranked queue");
        }
        if ("reveal".equals(action)) {
            Gameboard gameboard = rankedGameBoards.get(sessionId);
            if (gameboard != null) {
                int x = jsonNode.get("row").asInt();
                int y = jsonNode.get("column").asInt();
                gameboard.revealCell(x, y);
                gameboard.printGameboard();
                messagingTemplate.convertAndSend("/topic/ranked/" + sessionId, gameboard);
            } else {
                System.out.println("No gameboard found for session: " + sessionId);
            }
        }
    }

    private void handleDailySweepMode(JsonNode jsonNode, String action) {
        Gameboard dailySweepGameboard = dailySweepGameMode.getDailyGameboard();
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
            int x = jsonNode.get("row").asInt();
            int y = jsonNode.get("column").asInt();
            gameboard.placeBombs(x, y, Optional.empty());
            gameboard.revealCell(x, y);

            practiceGameBoards.put(sessionId, gameboard);
            messagingTemplate.convertAndSend("/topic/practice/" + sessionId, gameboard);
        } else if ("reveal".equals(action)) {
            Gameboard gameboard = practiceGameBoards.get(sessionId);
            if (gameboard != null) {
                int x = jsonNode.get("row").asInt();
                int y = jsonNode.get("column").asInt();
                gameboard.revealCell(x, y);
                gameboard.printGameboard();
                messagingTemplate.convertAndSend("/topic/practice/" + sessionId, gameboard);
            }
        }

        if ("gameOver".equals(action)) {
            Gameboard gameboard = practiceGameBoards.get(sessionId);
            if (gameboard != null) {
                // send num of clicks
                // send num of flags placed
                messagingTemplate.convertAndSend("/topic/practice/" + sessionId, "Game Over");
            }
        }
    }
}