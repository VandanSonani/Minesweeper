package com.example.minesweeperbackend;

import com.example.minesweeperbackend.Gameplay.Gameboard;
import com.example.minesweeperbackend.Gameplay.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinesweeperBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperBackendApplication.class, args);

        Player player = new Player("John");
        System.out.println(player.getName() + "'s gameboard");
        Gameboard gameboard = new Gameboard(10, 10, 10);
        gameboard.placeBombs();

    }

}