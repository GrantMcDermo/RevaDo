package com.revature.RevaDo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "todo_items")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private Boolean completed;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User taskCreator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primaryTask", orphanRemoval = true)
    @JsonManagedReference
    private List<Subtask> subtasks;
}
