package com.example.minesweeperbackend.gameplay;

public class Gameboard {
    int bombs;
    int rows;
    int columns;

    public Gameboard(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
    }

    public void printGameboard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(" # ");
            }
            System.out.println();
        }
    }
}