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
    //GOOGLE_CLOUD_PROJECT_ID=competitive-minesweeper-267bb
    //GOOGLE_APPLICATION_CREDENTIALS=C:\Users\princ\OneDrive\Documents\GitHub\Competitive Minesweeper\Minesweeper\minesweeper-backend\src\main\resources\private-key.json

}