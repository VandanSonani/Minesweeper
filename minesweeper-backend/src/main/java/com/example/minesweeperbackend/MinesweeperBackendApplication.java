package com.example.minesweeperbackend;

import com.example.minesweeperbackend.gameplay.Gameboard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinesweeperBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperBackendApplication.class, args);

        Gameboard.createGameBoard(10, 10, 10);
    }

}
