package com.example.minesweeperbackend.Gameplay;

public class Player {
    private final String name;
    // fixme might not be final because might need to be changed later
    private final Gameboard board = new Gameboard(10, 10, 10);

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Gameboard getGameboard() {
        return board;
    }
}
