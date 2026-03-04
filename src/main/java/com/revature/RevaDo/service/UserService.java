package com.revature.RevaDo.service;

import com.revature.RevaDo.DTO.AuthRequest;
import com.revature.RevaDo.entity.User;
import com.revature.RevaDo.exception.AuthFail;
import com.revature.RevaDo.exception.LoginFail;
import com.revature.RevaDo.repository.UserRepository;
import com.revature.RevaDo.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;

    public Map<String, String> validateCredentials(AuthRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        Map<String, String> responseMap = new HashMap<>();
        Optional<User> user = repo.findByUsernameAndPassword(username, password);
        if(user.isPresent()){
            UUID userId = user.get().getId();
            String token = jwtUtil.generateAccessToken(userId, username);
            responseMap.put("token", token);
            return responseMap;
        }
        throw new LoginFail("Login attempt failed");
    }

    public boolean validateToken(String token){
        if(token == null){
            throw new AuthFail("Token not found");
        }
        try{
            System.out.println(token);
            String tokenSplit = token.split(" ")[1];
            String id = jwtUtil.extractId(tokenSplit);
            System.out.println(id);
            String username = jwtUtil.extractUsername(tokenSplit);
            System.out.println(username);
            Optional<User> user = repo.findById(UUID.fromString(id));
            if(user.isPresent()){
                User foundUser = user.get();
                return foundUser.getId().equals(UUID.fromString(id)) && foundUser.getUsername().equals(username);
            }
            return false;
        } catch (JwtException e){
            throw new AuthFail("Token could not be parsed");
        }
    }

    public User register(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("ROLE_USER");
        return repo.save(user);
    }
}
