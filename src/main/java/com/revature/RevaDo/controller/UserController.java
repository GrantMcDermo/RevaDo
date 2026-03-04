package com.revature.RevaDo.controller;

import com.revature.RevaDo.DTO.AuthRequest;
import com.revature.RevaDo.entity.User;
import com.revature.RevaDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<User> register (@RequestBody AuthRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request.getUsername(), request.getPassword()));
    }
}
