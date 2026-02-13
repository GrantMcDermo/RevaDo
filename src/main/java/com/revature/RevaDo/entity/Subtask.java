package com.revature.RevaDo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "subtasks")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String subtaskName;
    private String subtaskDescription;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "todo_item_id")
    @JsonBackReference
    private Todo primaryTask;
}
