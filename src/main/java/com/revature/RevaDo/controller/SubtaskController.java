package com.revature.RevaDo.controller;

import com.revature.RevaDo.DTO.SubtaskRequestDTO;
import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.service.SubtaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subtasks")
public class SubtaskController {

    private final SubtaskService service;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<SubtaskResponseDTO>> getSubtasksForTask(
            @PathVariable UUID taskId,
            HttpServletRequest request
    ) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(service.getSubtasksForTask(taskId, userId));
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<SubtaskResponseDTO> createSubtask(
            @PathVariable UUID taskId,
            HttpServletRequest request,
            @Valid @RequestBody SubtaskRequestDTO requestDTO){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        SubtaskResponseDTO response = service.createSubtask(taskId, userId, requestDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public SubtaskResponseDTO updateSubtask(
            @PathVariable UUID id,
            HttpServletRequest request,
            @Valid @RequestBody SubtaskRequestDTO requestDTO
    ) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return service.updateSubtask(id, userId, requestDTO);
    }

    @PatchMapping("/{id}/complete")
    public SubtaskResponseDTO markComplete(
            @PathVariable UUID id,
            HttpServletRequest request
    ) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return service.markSubtaskComplete(id, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(
            @PathVariable UUID id,
            HttpServletRequest request
    ){
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        service.deleteSubtask(id, userId);
        return ResponseEntity.noContent().build();
    }
}
