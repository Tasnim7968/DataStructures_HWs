package edu.brooklyn.cisc3130.taskboard.repository;

import edu.brooklyn.cisc3130.taskboard.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByDeletedFalse();

    List<Task> findByDeletedTrue();
}