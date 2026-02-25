package com.revature.RevaDo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoRequestDTO {
    @NotBlank
    private String taskName;
    private String taskDescription;
}
