package com.example.minesweeperbackend.service;

import com.example.minesweeperbackend.dao.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    public Firestore firestore;

    public User createUser(User user) {
        try {
            ApiFuture<DocumentReference> users = firestore.collection("users").add(user);
            user.setId(users.get().getId());
            return user;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error creating user {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}