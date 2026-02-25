package com.revature.RevaDo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubtaskRequestDTO {
    @NotBlank
    private String subtaskName;
    private String subtaskDescription;
}

