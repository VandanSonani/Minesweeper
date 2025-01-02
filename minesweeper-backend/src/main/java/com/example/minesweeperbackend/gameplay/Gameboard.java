package com.example.minesweeperbackend.gameplay;

import java.util.Arrays;
import java.util.Optional;

public class Gameboard {
    private int bombsCount;
    private int rows;
    private int columns;
    String[][] gameboard;


    public Gameboard(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombsCount = bombs;
        gameboard = new String[rows][columns];
    }

    public int getRows(){
        return rows;
    }

    public int getColumns(){
        return columns;
    }

    public int getBombsCount(){
        return bombsCount;
    }

    public String[][] getGameboard(){
        return gameboard;
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
            gameboard[row][column] = "B";
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameboard[i][j] == null) {
                    gameboard[i][j] = "#";
                }
            }
        }

        printGameboard();
    }

    //TODO handle flag on frontend
//    public void placeFlag(int x, int y){
//        boolean[][] flag = new boolean[rows][columns];
//        for (int i = 0; i < x; i++) {
//            for (int j = 0; j < y; j++) {
//                if (gameboard[i][j].equals("#")) {
//                    gameboard[i][j] = "F";
//                    flag[i][j] = true;
//                    break;
//                }
//                if(gameboard[i][j].equals('F')){
//
//                }
//            }
//        }
//        printGameboard();
//    }

    public int checkAdjacentCells(int row, int column) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if (gameboard[i][j].equals("B")) {
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
        if (gameboard[row][column].equals("B")) {
            System.out.println("Game Over");
            return;
        }

        if (gameboard[row][column].equals("#")) {
            // check if there are bombs around
            int nearBombCount = checkAdjacentCells(row, column);
            // if there are bombs around it then reveal number
            if (nearBombCount > 0) {
                gameboard[row][column] = String.valueOf(nearBombCount);
            } else {
                // mark as revealed
                gameboard[row][column] = "E";
                // recursively reveal all adjacent cells
                int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
                int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

                for (int i = 0; i < 8; i++) {
                    revealCell(row + dRow[i], column + dCol[i]);
                }

            }
        }

    }

        // if number, reveal number
        // if empty, reveal empty



    public void printGameboard() {
        System.out.println("Gameboard:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameboard[i][j].equals("#")) {
                    System.out.print("# ");
                } else {
                    System.out.print(gameboard[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println(Arrays.deepToString(gameboard));
    }



}