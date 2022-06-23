package com.etaskify.repository;

import com.etaskify.model.TaskAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAssignRepository extends JpaRepository<TaskAssign, Long> {
}
