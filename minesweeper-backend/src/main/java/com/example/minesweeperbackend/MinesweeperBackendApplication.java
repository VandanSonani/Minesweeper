package com.example.minesweeperbackend;

import com.example.minesweeperbackend.gameplay.Gameboard;
import com.example.minesweeperbackend.gameplay.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinesweeperBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperBackendApplication.class, args);

        Player player = new Player("John");
        System.out.println(player.getName() + "'s gameboard");
        Gameboard gameboard = new Gameboard(15, 15, 40);
        gameboard.placeBombs();
        gameboard.revealCell(3,3);
        gameboard.printGameboard();
    }

}