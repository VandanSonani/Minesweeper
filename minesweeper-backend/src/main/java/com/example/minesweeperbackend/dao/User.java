package com.example.minesweeperbackend.dao;

import com.example.minesweeperbackend.gameplay.GameHistory;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


@Data

public class User {

    @DocumentId
    private String id;
    private String displayName;
    private String email;
    private Rank rank;
    private int coins;
    private List<GameHistory> career;

    @DateTimeFormat
    private Object createdAt;
}
