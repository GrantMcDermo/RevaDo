package com.revature.RevaDo.controller;

import com.revature.RevaDo.DTO.TodoRequestDTO;
import com.revature.RevaDo.DTO.TodoResponseDTO;
import com.revature.RevaDo.entity.CustomUserDetails;
import com.revature.RevaDo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTask(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody TodoRequestDTO request){
        return ResponseEntity.ok(service.createTask(user.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getTasks(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(service.getUserTasks(user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails user){
        service.deleteTask(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public TodoResponseDTO updateTodo(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody TodoRequestDTO request
    ) {
        return service.updateTask(id, user.getId(), request);
    }


    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponseDTO> markTaskComplete(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(service.markTaskComplete(id, user.getId()));
    }


}
