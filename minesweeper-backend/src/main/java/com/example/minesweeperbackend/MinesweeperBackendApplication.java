package com.example.minesweeperbackend;

import com.example.minesweeperbackend.gameplay.Gameboard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class MinesweeperBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperBackendApplication.class, args);

        Gameboard gameboard = new Gameboard(15, 15, 226);
        gameboard.placeBombs(Optional.empty());
        gameboard.revealCell(0,0);
        gameboard.printGameboard();
        System.out.println("is the gameboard solved?: " + gameboard.isPuzzleSolved());

    }
    //GOOGLE_CLOUD_PROJECT_ID=competitive-minesweeper-267bb
    //GOOGLE_APPLICATION_CREDENTIALS=C:\Users\princ\OneDrive\Documents\GitHub\Competitive Minesweeper\Minesweeper\minesweeper-backend\src\main\resources\private-key.json

}