package com.revature.RevaDo.service;

import com.revature.RevaDo.DTO.SubtaskRequestDTO;
import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.entity.Subtask;
import com.revature.RevaDo.entity.Todo;
import com.revature.RevaDo.repository.SubtaskRepository;
import com.revature.RevaDo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SubtaskService {

    private final SubtaskRepository repo;
    private final TodoRepository taskRepo;

    public SubtaskResponseDTO createSubtask(UUID taskId, UUID userId, SubtaskRequestDTO request){
        Todo task = taskRepo.findByIdAndTaskCreator_Id(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));
        Subtask subtask = new Subtask();
        subtask.setTitle(request.getTitle());
        subtask.setDescription(request.getDescription());
        subtask.setPrimaryTask(task);
        subtask.setCompleted(false);

        Subtask saved = repo.save(subtask);

        return mapToDTO(saved);
    }

    public SubtaskResponseDTO updateSubtask(UUID subtaskId, UUID userId, SubtaskRequestDTO request){
        Subtask subtask = repo.findByIdAndPrimaryTask_TaskCreator_Id(subtaskId, userId)
                .orElseThrow(() -> new RuntimeException("Subtask not found!"));
        subtask.setTitle(request.getTitle());
        subtask.setDescription(request.getDescription());
        Subtask saved = repo.save(subtask);

        return mapToDTO(saved);
    }

    public SubtaskResponseDTO markSubtaskComplete(UUID subtaskId, UUID userId) {

        Subtask subtask = repo
                .findByIdAndPrimaryTask_TaskCreator_Id(subtaskId, userId)
                .orElseThrow(() -> new RuntimeException("Subtask not found"));
        Boolean status = subtask.getCompleted();
        subtask.setCompleted(!status);

        Subtask saved = repo.save(subtask);

        autoCompleteParentIfNecessary(saved.getPrimaryTask());

        return mapToDTO(saved);
    }

    public void deleteSubtask(UUID subtaskId, UUID userId){
        Subtask subtask = repo.findByIdAndPrimaryTask_TaskCreator_Id(subtaskId, userId).orElseThrow(() -> new RuntimeException("Subtask not found"));
        repo.delete(subtask);
    }

    //If it detects that all subtasks of a given task are marked as complete, then the main task will auto-complete.
    private void autoCompleteParentIfNecessary(Todo task) {

        List<Subtask> subtasks = task.getSubtasks();

        if(subtasks.isEmpty()){
            return;
        }

        boolean allComplete = task.getSubtasks()
                .stream()
                .allMatch(Subtask::getCompleted);

        if (allComplete && !task.getCompleted()) {
            task.setCompleted(true);
            taskRepo.save(task);
        }
    }

    private SubtaskResponseDTO mapToDTO(Subtask subtask){
        return new SubtaskResponseDTO(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getDescription(),
                subtask.getCompleted()
        );
    }

}
