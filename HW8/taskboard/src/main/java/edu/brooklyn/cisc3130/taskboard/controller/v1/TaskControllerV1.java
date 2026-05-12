package edu.brooklyn.cisc3130.taskboard.controller.v1;

import edu.brooklyn.cisc3130.taskboard.dto.TaskRequest;
import edu.brooklyn.cisc3130.taskboard.dto.TaskResponse;
import edu.brooklyn.cisc3130.taskboard.exception.InvalidTaskDataException;
import edu.brooklyn.cisc3130.taskboard.model.Task;
import edu.brooklyn.cisc3130.taskboard.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskControllerV1 {

    private final TaskService taskService;

    public TaskControllerV1(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(toList());

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                TaskResponse.fromEntity(taskService.getTaskById(id))
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        Task task = convertRequestToTask(taskRequest);

        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TaskResponse.fromEntity(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        Task task = convertRequestToTask(taskRequest);

        return ResponseEntity.ok(
                TaskResponse.fromEntity(taskService.updateTask(id, task))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task soft deleted successfully");
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreTask(@PathVariable Integer id) {
        taskService.restoreTask(id);
        return ResponseEntity.ok("Task restored successfully");
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<TaskResponse>> getDeletedTasks() {
        List<TaskResponse> tasks = taskService.getDeletedTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(toList());

        return ResponseEntity.ok(tasks);
    }

    private Task convertRequestToTask(TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.getCompleted() != null ? taskRequest.getCompleted() : false);

        try {
            task.setPriority(Task.Priority.valueOf(
                    taskRequest.getPriority() != null
                            ? taskRequest.getPriority().toUpperCase()
                            : "MEDIUM"
            ));
        } catch (IllegalArgumentException ex) {
            throw new InvalidTaskDataException("Priority must be LOW, MEDIUM, or HIGH");
        }

        return task;
    }
}