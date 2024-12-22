package com.example.minesweeperbackend.Gameplay;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameboardTest {

    @Test
    void testGameboardInitialization() {
        Gameboard gameboard = new Gameboard(10, 10, 10);
        assertEquals(10, gameboard.rows);
        assertEquals(10, gameboard.columns);
        assertEquals(10, gameboard.bombsCount);
    }

    @Test
    void testPrintGameboard() {
        Gameboard gameboard = new Gameboard(2, 2, 1);
        gameboard.placeBombs();
    }

    // TODO, finish tests
    // Test for bomb placement

    @Test
    void testBombPlacement() {
        Gameboard gameboard = new Gameboard(10, 10, 10);
    }

    // Test for bomb count

    // Test for flag placement

    // Test for flag count

    // Test for board click

    // Test for board click on a bomb

    // Test to not allow board click on a flag

    // Test for board click on a number cell

    // Test for board click on an empty cell

    // Test for click on a already revealed cell

    // Test to not allow flag on a already revealed cell

}