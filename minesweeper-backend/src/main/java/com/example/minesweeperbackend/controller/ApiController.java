package com.example.minesweeperbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/")
    public String getPage() {
        return "Welcome to the Minesweeper API";
    }

    @GetMapping("/start")
    public String startGame() {
        return "Game started";
    }




}
