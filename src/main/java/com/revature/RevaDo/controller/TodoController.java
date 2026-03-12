package com.revature.RevaDo.controller;

import com.revature.RevaDo.DTO.TodoRequestDTO;
import com.revature.RevaDo.DTO.TodoResponseDTO;
import com.revature.RevaDo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService service;

    /*
        Each endpoint will extract the UUID from the JWT interceptor.

        createTask takes a DTO as an argument, mainly receiving the title and description of a ToDo task.
     */
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTask(HttpServletRequest request, @Valid @RequestBody TodoRequestDTO requestDTO){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(service.createTask(userId, requestDTO));
    }
// getTasks only needs the UUID of the active token to find all the necessary tasks.
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getTasks(HttpServletRequest request){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(service.getUserTasks(userId));
    }
// deleteTask will receive the Task Id and the User Id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id, HttpServletRequest request){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        service.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }
// updateTodo will receive the Task Id, User Id, and the task title and description.
    @PutMapping("/{id}")
    public TodoResponseDTO updateTodo(
            @PathVariable UUID id,
            HttpServletRequest request,
            @Valid @RequestBody TodoRequestDTO requestDTO
    ) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return service.updateTask(id, userId, requestDTO);
    }
    // markTaskComplete receives the task Id and the user Id.
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponseDTO> markTaskComplete(@PathVariable UUID id, HttpServletRequest request){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(service.markTaskComplete(id, userId));
    }
}
