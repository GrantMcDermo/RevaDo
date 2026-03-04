package com.revature.RevaDo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SubtaskResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Boolean completed;
}
