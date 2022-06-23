package com.etaskify.repository;

import com.etaskify.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query(value = "select t.id , t.title, t.description, t.status, t.dead_line as deadLine, t.created , t.end_date as endDate from task t " +
            "inner join task_assign ta on t.id = ta.task_id " +
            "inner join task_assign_users tau on ta.id = tau.task_assign_id " +
            "inner join users u on tau.users_id = u.id where u.email =:email", nativeQuery = true)
    List<TaskProjection> findAllTaskOfUser(@Param("email") String email);
}
