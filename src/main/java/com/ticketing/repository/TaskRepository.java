package com.ticketing.repository;

import com.ticketing.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAll();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'Completed'")
    int totalNonCompletedTasks(String projectCode);

    @Query(value = "SELECT COUNT (*) FROM tasks t JOIN projecs p on t.project_id = p.id WHERE p.project_code = ?1 AND t.task_status = 'COMPLETE'", nativeQuery = true)
    int totalCompletedTasks(String projectCode);

}