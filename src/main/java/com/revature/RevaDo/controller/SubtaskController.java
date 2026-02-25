package com.revature.RevaDo.controller;

import com.revature.RevaDo.DTO.SubtaskRequestDTO;
import com.revature.RevaDo.DTO.SubtaskResponseDTO;
import com.revature.RevaDo.entity.CustomUserDetails;
import com.revature.RevaDo.service.SubtaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubtaskController {

    private final SubtaskService service;

    @PostMapping("/todos/{taskId}/subtasks")
    public ResponseEntity<SubtaskResponseDTO> createSubtask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody SubtaskRequestDTO request){
        SubtaskResponseDTO response = service.createSubtask(taskId, user.getId(), request);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/subtasks/{id}")
    public SubtaskResponseDTO updateSubtask(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody SubtaskRequestDTO request
    ) {
        return service.updateSubtask(id, user.getId(), request);
    }

    @PatchMapping("/subtasks/{id}/complete")
    public SubtaskResponseDTO markComplete(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return service.markSubtaskComplete(id, user.getId());
    }

    @DeleteMapping("/subtasks/{id}")
    public ResponseEntity<Void> deleteSubtask(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        service.deleteSubtask(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
