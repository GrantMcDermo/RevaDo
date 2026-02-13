package com.revature.RevaDo.service;

import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.repository.TodoRepository;
import com.revature.RevaDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;
    private final UserRepository userRepo;

    public TodoService(TodoRepository repo, UserRepository userRepo){
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Todo createTask(Todo task){
        return repo.save(task);
    }

    public Todo markTaskComplete(Todo task){
        task.setStatus(true);
        return repo.save(task);

    }
}
