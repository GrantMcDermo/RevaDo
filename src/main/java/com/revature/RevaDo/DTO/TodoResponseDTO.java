package com.revature.RevaDo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TodoResponseDTO {
    private UUID id;
    private String taskName;
    private String taskDescription;
    private Boolean status;
    private List<SubtaskResponseDTO> subtasks;
}
