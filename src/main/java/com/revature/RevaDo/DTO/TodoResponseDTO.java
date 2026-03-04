package com.revature.RevaDo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TodoResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Boolean completed;
    private List<SubtaskResponseDTO> subtasks;
}
