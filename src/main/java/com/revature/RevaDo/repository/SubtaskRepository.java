package com.revature.RevaDo.repository;

import com.revature.RevaDo.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {
    Optional<Subtask> findByIdAndPrimaryTask_TaskCreator_Id(UUID subtaskId, UUID userId);
}
