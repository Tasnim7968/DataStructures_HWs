package edu.brooklyn.cisc3130.taskboard.controller;

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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {

        return taskService.getAllTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Integer id) {

        return TaskResponse.fromEntity(
                taskService.getTaskById(id)
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
    public TaskResponse updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskRequest taskRequest
    ) {

        Task task = convertRequestToTask(taskRequest);

        return TaskResponse.fromEntity(
                taskService.updateTask(id, task)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Integer id
    ) {

        taskService.deleteTask(id);

        return ResponseEntity.ok(
                "Task soft deleted successfully"
        );
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreTask(
            @PathVariable Integer id
    ) {

        taskService.restoreTask(id);

        return ResponseEntity.ok(
                "Task restored successfully"
        );
    }

    @GetMapping("/deleted")
    public List<TaskResponse> getDeletedTasks() {

        return taskService.getDeletedTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(toList());
    }

    private Task convertRequestToTask(TaskRequest taskRequest) {

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        task.setCompleted(
                taskRequest.getCompleted() != null
                        ? taskRequest.getCompleted()
                        : false
        );

        try {

            task.setPriority(
                    Task.Priority.valueOf(
                            taskRequest.getPriority() != null
                                    ? taskRequest.getPriority().toUpperCase()
                                    : "MEDIUM"
                    )
            );

        } catch (IllegalArgumentException ex) {

            throw new InvalidTaskDataException(
                    "Priority must be LOW, MEDIUM, or HIGH"
            );
        }

        return task;
    }
}