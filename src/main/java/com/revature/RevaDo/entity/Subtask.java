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
    private String title;
    private String description;
    private Boolean completed;
    @ManyToOne
    @JoinColumn(name = "todo_item_id", nullable = false)
    @JsonBackReference
    private Todo primaryTask;
}
