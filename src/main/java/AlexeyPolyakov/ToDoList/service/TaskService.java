package AlexeyPolyakov.ToDoList.service;

import AlexeyPolyakov.ToDoList.model.Task;
import AlexeyPolyakov.ToDoList.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Создание задачи
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Получение задачи по ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Получение всех задач
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Обновление задачи
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    // Удаление задачи
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}