package com.example.minesweeperbackend.gameplay;

import java.util.Arrays;

public class Gameboard {
    private int bombsCount;
    private int rows;
    private int columns;
    String[][] gameboard;


    private Gameboard(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombsCount = bombs;
        gameboard = new String[rows][columns];
    }

    public void getRows(){

    }

    public void getColumns(){

    }

    public void getBombsCount(){
        gameboard.
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

    public void placeFlag(int x, int y){
        boolean[][] flag = new boolean[rows][columns];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (gameboard[i][j].equals("#")) {
                    gameboard[i][j] = "F";
                    flag[i][j] = true;
                    break;
                }
            }
        }
        printGameboard();
    }

//    revealCell(boolean[][] bombLocations, int row, int column) {
//        // if bomb, game over
//        if (bombLocations[row][column]) {
//            System.out.println("Game Over");
//            return;
//        }
//        else if (bombLocations[row][column] != true ) {
//            System.out.println("Game Over");
//            return;
//        }
//        // if number, reveal number
//        // if empty, reveal empty
//    }


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