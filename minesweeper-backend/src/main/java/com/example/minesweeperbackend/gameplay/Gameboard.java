package com.example.minesweeperbackend.gameplay;

import java.sql.SQLOutput;

public class Gameboard {
    // flags, bombs, board, rows, columns

    int bombs = 5;

    int rows;
    int columns;

    public void createGameBoard(int rows, int columns, int bombs) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println(" # ");
            }
            System.out.println("\n");
        }
    }
}
