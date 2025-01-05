package com.example.minesweeperbackend.gameplay;

public class GameHistory {
    private final String username;
    private final String gameMode;
    private final String gameResult;
    private final String gameDate;

    public GameHistory(String username, String gameMode, String gameResult, String gameDate) {
        this.username = username;
        this.gameMode = gameMode;
        this.gameResult = gameResult;
        this.gameDate = gameDate;
    }

    public String getUsername() {
        return username;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGameResult() {
        return gameResult;
    }

    public String getGameDate() {
        return gameDate;
    }
}
