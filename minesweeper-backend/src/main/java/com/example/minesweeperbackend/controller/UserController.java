package com.example.minesweeperbackend.controller;


import com.example.minesweeperbackend.dao.User;
import com.example.minesweeperbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/v1/user")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Creating user..." + user);
        return ResponseEntity.ok(userService.createUser(user));
    }
}
