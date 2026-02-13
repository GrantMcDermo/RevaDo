package com.revature.RevaDo.controller;

import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService service;

    @PostMapping
    public Todo createTask(@RequestBody Todo task){
        return service.createTask(task);
    }

    @PutMapping
    public ResponseEntity<Todo> markTaskComplete(@RequestBody Todo task){
        Todo completedTask = service.markTaskComplete(task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }


}
