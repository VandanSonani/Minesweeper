package com.example.minesweeperbackend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/")
    public String getPage() {
        return "Welcome to the Minesweeper API";
    }
}
