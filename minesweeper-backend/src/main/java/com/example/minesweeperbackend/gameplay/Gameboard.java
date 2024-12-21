package com.example.minesweeperbackend.gameplay;

public class Gameboard {
    int bombsCount;
    int rows;
    int columns;

    public Gameboard(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombsCount = bombs;
    }

    // place bomb function
    // randomly place bombbCount in row x columns
    // after your place bombs print gameboard
    // 'B' for bomb
    // Math.random to generate random numbers
    //print board in bomb

    public void placeBombs() {
        boolean[][] bombLocations = new boolean[rows][columns];

        for (int i = 0; i < bombsCount; i++) {
            int row, column;
            do {
                row = (int) (Math.random() * rows);
                column = (int) (Math.random() * columns);
            } while (bombLocations[row][column]);

            bombLocations[row][column] = true;
            System.out.println("Bomb placed at row: " + row + " column: " + column);
        }

        printGameboard(bombLocations);
    }

    public void printGameboard(boolean[][] bombLocations) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (bombLocations[i][j]) {
                    System.out.print(" B ");
                } else {
                    System.out.print(" # ");
                }
            }
            System.out.println();
        }
    }



}