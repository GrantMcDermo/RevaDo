package com.revature.RevaDo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoRequestDTO {
    @NotBlank
    private String title;
    private String description;
    private Boolean completed;
}
