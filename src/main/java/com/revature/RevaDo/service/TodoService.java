package com.revature.RevaDo.service;

import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.DTO.TodoRequestDTO;
import com.revature.RevaDo.DTO.TodoResponseDTO;
import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.entity.User;
import com.revature.RevaDo.repository.TodoRepository;
import com.revature.RevaDo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;
    private final UserRepository userRepo;

    public TodoResponseDTO createTask(UUID userId, TodoRequestDTO request){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Todo task = new Todo();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        task.setTaskCreator(user);
        return mapToDTO(repo.save(task));
    }

    public List<TodoResponseDTO> getUserTasks(UUID userId){
        return repo.findAllByTaskCreator_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    public TodoResponseDTO updateTask(UUID taskId, UUID userId, TodoRequestDTO request){
        Todo task = repo.findByIdAndTaskCreator_Id(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        return mapToDTO(repo.save(task));
    }

    @Transactional
    public TodoResponseDTO markTaskComplete(UUID taskId, UUID userId){
        Todo task = repo.findByIdAndTaskCreator_Id(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Boolean status = task.getCompleted();
        task.setCompleted(!status);
        return mapToDTO(repo.save(task));

    }

    public void deleteTask(UUID taskId, UUID userId){
        Todo task = repo.findByIdAndTaskCreator_Id(taskId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!"));
        repo.delete(task);
    }

    private TodoResponseDTO mapToDTO(Todo task){
        List<SubtaskResponseDTO> subtasks = Optional.ofNullable(task.getSubtasks())
                .orElse(Collections.emptyList())
                .stream()
                .map(s -> new SubtaskResponseDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getCompleted()
                ))
                .toList();

        return new TodoResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted(),
                subtasks
        );
    }
}
