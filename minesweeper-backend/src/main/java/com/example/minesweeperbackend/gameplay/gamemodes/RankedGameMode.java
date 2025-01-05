package com.example.minesweeperbackend.gameplay.gamemodes;

import com.example.minesweeperbackend.dao.User;
import com.example.minesweeperbackend.gameplay.Gameboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
public class RankedGameMode {

    private int difficulty;
    private int score;
    private long time;
    private long timePerClick;
    private int experience;
    private int coins;

    @Getter
    @Setter
    private User[] users;

    private Gameboard gameboard = new Gameboard(12, 15, 40);

    public void initializeRankedGameMode() {
        gameboard.placeBombs(Optional.empty());
    }


}
