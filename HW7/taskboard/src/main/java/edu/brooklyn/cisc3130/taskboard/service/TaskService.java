package edu.brooklyn.cisc3130.taskboard.service;

import edu.brooklyn.cisc3130.taskboard.exception.TaskNotFoundException;
import edu.brooklyn.cisc3130.taskboard.model.Task;
import edu.brooklyn.cisc3130.taskboard.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findByDeletedFalse();
    }

    public List<Task> getDeletedTasks() {
        return taskRepository.findByDeletedTrue();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {

        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.getCompleted());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTask(Integer id) {

        Task task = getTaskById(id);

        task.setDeleted(true);

        taskRepository.save(task);
    }

    public void restoreTask(Integer id) {

        Task task = getTaskById(id);

        task.setDeleted(false);

        taskRepository.save(task);
    }
}