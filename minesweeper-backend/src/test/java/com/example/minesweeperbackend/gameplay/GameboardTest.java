package com.example.minesweeperbackend.Gameplay;

import com.example.minesweeperbackend.gameplay.Gameboard;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameboardTest {

    @Test
    public void testGameboardInitialization() {
        Gameboard gameboard = new Gameboard(10, 10, 10);
        assertEquals(10, gameboard.getRows());
        assertEquals(10, gameboard.getColumns());
        assertEquals(10, gameboard.getBombsCount());
    }

    @Test
    public void testPrintGameboard() {
        Gameboard gameboard = new Gameboard(2, 2, 1);
        gameboard.placeBombs(Optional.empty());
    }

    // TODO, finish tests
    // Test for bomb placement & bomb count

    @Test
    public void testBombPlacement() {
        Gameboard gameboard = new Gameboard(6, 6, 3);
        gameboard.placeBombs(Optional.empty());
        int bombCount = 0;
        for (int i = 0; i < gameboard.getRows(); i++) {
            for (int j = 0; j < gameboard.getColumns(); j++) {
                if (gameboard.getGameboard()[i][j] == "B") {
                    bombCount++;
                }
            }
        }
        assertEquals(3, bombCount);
    }

    // test board size
    @Test
    public void testBoardSize() {
        Gameboard gameboard = new Gameboard(6, 6, 3);
        assertEquals(6, gameboard.getRows());
        assertEquals(6, gameboard.getColumns());
    }

    // Test for flag placement - front end

    // Test for flag count - front end

    // Test for board click - front end

    // Test for board click on a bomb - front end

    // Test to not allow board click on a flag - front end

    // Test for board click on a number cell

    // Test for board click on an empty cell

    // Test for click on a already revealed cell

    // Test to not allow flag on a already revealed cell

    // Test for clicking bombs
    @Test
    public void testClickingBombs() {
        Gameboard gameboard = new Gameboard(15, 15, 40);
        gameboard.placeBombs(Optional.of(12345));
        gameboard.revealCell(0, 4);
        assertEquals("B", gameboard.getGameboard()[0][4]);
    }

}