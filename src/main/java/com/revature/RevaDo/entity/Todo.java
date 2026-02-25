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
    private String taskName;
    private String taskDescription;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User taskCreator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo_item", orphanRemoval = true)
    @JsonManagedReference
    private List<Subtask> subtasks;
}
