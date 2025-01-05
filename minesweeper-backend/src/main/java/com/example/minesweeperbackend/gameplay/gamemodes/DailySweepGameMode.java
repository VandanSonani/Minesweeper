package com.example.minesweeperbackend.gameplay.gamemodes;

import com.example.minesweeperbackend.gameplay.Gameboard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@Slf4j
public class DailySweepGameMode {

    private Gameboard dailyGameboard;
    private LocalDate lastGeneratedDate;

    @Getter
    private long timeLeftUntilNextBoard;

    @PostConstruct
    public void init() {
        try {
            generateNewGameboard();
        } catch (Exception e) {
            log.error("Failed to generate new gameboard", e);
            throw new RuntimeException("Failed to generate new gameboard", e);
        }
    }

    private void generateNewGameboard() {
        try {
            LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
            int seed = currentDate.hashCode();
            dailyGameboard = new Gameboard(15, 15, 40);
            dailyGameboard.placeBombs(Optional.of(seed));
            lastGeneratedDate = currentDate;
            updateTimeLeftUntilNextBoard();
            System.out.println("New daily gameboard generated with seed: " + seed);
        } catch (Exception e) {
            log.error("Error generating new gameboard", e);
            throw new RuntimeException("Error generating new gameboard", e);
        }
    }

    private void updateTimeLeftUntilNextBoard() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime nextMidnight = now.truncatedTo(ChronoUnit.DAYS).plusDays(1);
        timeLeftUntilNextBoard = ChronoUnit.SECONDS.between(now, nextMidnight);
    }

    public Gameboard getDailyGameboard() {
        LocalDate currentDate = LocalDate.now(ZoneOffset.UTC);
        if (!currentDate.equals(lastGeneratedDate)) {
            generateNewGameboard();
        } else {
            updateTimeLeftUntilNextBoard();
        }
        return dailyGameboard;
    }



}