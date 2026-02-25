package com.revature.RevaDo.service;

import com.revature.RevaDo.DTO.SubtaskRequestDTO;
import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.entity.Subtask;
import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.repository.SubtaskRepository;
import com.revature.RevaDo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository repo;
    private final TodoRepository taskRepo;

    public SubtaskResponseDTO createSubtask(UUID taskId, UUID userId, SubtaskRequestDTO request){
        Todo task = taskRepo.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));
        Subtask subtask = new Subtask();
        subtask.setSubtaskName(request.getSubtaskName());
        subtask.setSubtaskDescription(request.getSubtaskDescription());
        subtask.setPrimaryTask(task);
        subtask.setStatus(false);

        Subtask saved = repo.save(subtask);

        return mapToDTO(saved);
    }

    public SubtaskResponseDTO updateSubtask(UUID subtaskId, UUID userId, SubtaskRequestDTO request){
        Subtask subtask = repo.findByIdAndTodoUserId(subtaskId, userId)
                .orElseThrow(() -> new RuntimeException("Subtask not found!"));
        subtask.setSubtaskName(request.getSubtaskName());
        subtask.setSubtaskDescription(request.getSubtaskDescription());
        Subtask saved = repo.save(subtask);

        return mapToDTO(saved);
    }

    public SubtaskResponseDTO markSubtaskComplete(UUID subtaskId, UUID userId) {

        Subtask subtask = repo
                .findByIdAndTodoUserId(subtaskId, userId)
                .orElseThrow(() -> new RuntimeException("Subtask not found"));

        subtask.setStatus(true);

        Subtask saved = repo.save(subtask);

        autoCompleteParentIfNecessary(saved.getPrimaryTask());

        return mapToDTO(saved);
    }

    public void deleteSubtask(UUID subtaskId, UUID userId){
        Subtask subtask = repo.findByIdAndTodoUserId(subtaskId, userId).orElseThrow(() -> new RuntimeException("Subtask not found"));
        repo.delete(subtask);
    }

    private void autoCompleteParentIfNecessary(Todo task) {

        boolean allComplete = task.getSubtasks()
                .stream()
                .allMatch(Subtask::getStatus);

        if (allComplete) {
            task.setStatus(true);
        }
    }

    private SubtaskResponseDTO mapToDTO(Subtask subtask){
        return new SubtaskResponseDTO(
                subtask.getId(),
                subtask.getSubtaskName(),
                subtask.getSubtaskDescription(),
                subtask.getStatus()
        );
    }

}
