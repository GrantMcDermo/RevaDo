package com.revature.RevaDo.repository;

import com.revature.RevaDo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findAllByTaskCreator_Id(UUID userId);
    Optional<Todo> findByIdAndTaskCreator_Id(UUID id, UUID userId);
}
