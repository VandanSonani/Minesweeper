package com.example.minesweeperbackend.gameplay;

import lombok.Getter;

import java.util.Optional;
import java.util.Random;


@Getter
public class Gameboard {
    private final int bombsCount;
    private final int rows;
    private final int columns;
    String[][] gameBoard;


    public Gameboard(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombsCount = bombs;

        if (bombs > rows * columns) {
            throw new IllegalArgumentException("Bomb count cannot be greater than the total number of cells.");
        }

        gameBoard = new String[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombsCount() {
        return bombsCount;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }


    // place bomb function
    // randomly place bombbCount in row x columns
    // after your place bombs print gameboard
    // 'B' for bomb
    // Math.random to generate random numbers
    //print board in bomb

    private boolean isWithinSafeZone(int row, int column, int x, int y) {
        return row >= x - 1 && row <= x + 1 && column >= y - 1 && column <= y + 1;
    }

    public void placeBombs(Integer x, Integer y, Optional<Integer> seed) {
        boolean[][] bombLocations = new boolean[rows][columns];
        Random random;

        if (seed.isPresent()) {
            random = new Random(seed.get());
        } else {
            random = new Random();
        }

        for (int i = 0; i < bombsCount; i++) {
            int row, column;
            do {
                row = random.nextInt(rows);
                column = random.nextInt(columns);
            } while (bombLocations[row][column] || isWithinSafeZone(row, column, x, y));

            bombLocations[row][column] = true;
            gameBoard[row][column] = "B";
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] == null) {
                    gameBoard[i][j] = "#";
                }
            }
        }
    }

    public int checkAdjacentCells(int row, int column) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if (gameBoard[i][j].equals("B")) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void revealCell(int row, int column) {
        // Check for out-of-bounds
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            return;
        }

        // if bomb, game over
        if (gameBoard[row][column].equals("B")) {
            System.out.println("Game Over");
            return;
        }

        if (gameBoard[row][column].equals("#")) {
            // check if there are bombs around
            int nearBombCount = checkAdjacentCells(row, column);
            // if there are bombs around it then reveal number
            if (nearBombCount > 0) {
                gameBoard[row][column] = String.valueOf(nearBombCount);
            } else {
                // mark as revealed
                gameBoard[row][column] = "E";
                // recursively reveal all adjacent cells
                int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
                int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

                for (int i = 0; i < 8; i++) {
                    revealCell(row + dRow[i], column + dCol[i]);
                }

            }
        }
    }

    public boolean isPuzzleSolved() {
        // a puzzle is solved if, there are flags on all bombs OR all non bomb-cells are revealed
        int flagCount = 0;
        int revealedCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].equals("F")) {
                    flagCount++;
                } else if (gameBoard[i][j].equals("E") || gameBoard[i][j].matches("\\d+")) {
                    revealedCount++;
                }
            }
        }

        return flagCount == bombsCount || revealedCount == (rows * columns - bombsCount);
    }

    public void printGameboard() {
        System.out.println("Gameboard:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j].equals("#")) {
                    System.out.print("# ");
                } else {
                    System.out.print(gameBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}