package com.revature.RevaDo.service;

import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.DTO.TodoRequestDTO;
import com.revature.RevaDo.DTO.TodoResponseDTO;
import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.entity.User;
import com.revature.RevaDo.repository.TodoRepository;
import com.revature.RevaDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;
    private final UserRepository userRepo;

    public TodoResponseDTO createTask(UUID userId, TodoRequestDTO request){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Todo task = new Todo();
        task.setTaskName(request.getTaskName());
        task.setTaskDescription(request.getTaskDescription());
        task.setStatus(false);
        task.setTaskCreator(user);
        return mapToDTO(repo.save(task));
    }

    public List<TodoResponseDTO> getUserTasks(UUID userId){
        return repo.findAllByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    public TodoResponseDTO updateTask(UUID taskId, UUID userId, TodoRequestDTO request){
        Todo task = repo.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTaskName(request.getTaskName());
        task.setTaskDescription(request.getTaskDescription());
        return mapToDTO(repo.save(task));
    }

    public TodoResponseDTO markTaskComplete(UUID taskId, UUID userId){
        Todo task = repo.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(true);
        return mapToDTO(repo.save(task));

    }

    public void deleteTask(UUID taskId, UUID userId){
        Todo task = repo.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        repo.delete(task);
    }

    private TodoResponseDTO mapToDTO(Todo task){
        List<SubtaskResponseDTO> subtasks = task.getSubtasks()
                .stream()
                .map(s -> new SubtaskResponseDTO(
                        s.getId(),
                        s.getSubtaskName(),
                        s.getSubtaskDescription(),
                        s.getStatus()
                ))
                .toList();

        return new TodoResponseDTO(
                task.getId(),
                task.getTaskName(),
                task.getTaskDescription(),
                task.getStatus(),
                subtasks
        );
    }
}
