package AlexeyPolyakov.ToDoList.controller;

import AlexeyPolyakov.ToDoList.model.Task;
import AlexeyPolyakov.ToDoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "API для управления задачами")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание задачи", description = "Создает новую задачу")
    @PostMapping
    public ResponseEntity<Task> createTask(
            @Parameter(description = "Данные задачи") @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Получение задачи по ID", description = "Возвращает задачу по её ID")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID задачи") @PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Получение всех задач", description = "Возвращает список всех задач")
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "Обновление задачи", description = "Обновляет задачу по её ID")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID задачи") @PathVariable Long id,
            @Parameter(description = "Обновленные данные задачи") @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(summary = "Удаление задачи", description = "Удаляет задачу по её ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID задачи") @PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Фильтрация задач по статусу", description = "Возвращает задачи с фильтрацией по статусу и пагинацией")
    @GetMapping("/filter")
    public ResponseEntity<Page<Task>> getTasksByStatus(
            @Parameter(description = "Статус задачи (true - выполнено, false - не выполнено)") @RequestParam boolean completed,
            @Parameter(description = "Параметры пагинации (page, size)") Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByStatus(completed, pageable);
        return ResponseEntity.ok(tasks);
    }



}