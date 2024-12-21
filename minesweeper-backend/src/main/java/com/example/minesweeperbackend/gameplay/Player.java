package com.example.minesweeperbackend.gameplay;

public class Player {
    private String name;
    private Gameboard board;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Gameboard getGameboard() {
        return board;
    }

    public void setGameboard(Gameboard board) {
        this.board = board;
    }

//    name, board
}
