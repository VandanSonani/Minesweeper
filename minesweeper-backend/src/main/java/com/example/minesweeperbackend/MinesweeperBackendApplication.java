package com.example.minesweeperbackend;

import com.example.minesweeperbackend.gameplay.Gameboard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class MinesweeperBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperBackendApplication.class, args);

        Gameboard gameboard = new Gameboard(15, 15, 40);
        gameboard.placeBombs(Optional.empty());
        gameboard.revealCell(3,3);
        gameboard.printGameboard();
    }

}