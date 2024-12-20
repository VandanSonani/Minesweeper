package com.example.minesweeperbackend.gameplay;

public class Player {

    private Gameboard gameboard;
    private String name;

    public Player (String name) {
        this.gameboard = new Gameboard(10, 10, 10);
        this.name = "Player";
    }

    public Gameboard getGameboard() {
        return gameboard;
    }
}
