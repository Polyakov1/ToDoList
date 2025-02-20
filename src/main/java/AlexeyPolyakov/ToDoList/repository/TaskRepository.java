package AlexeyPolyakov.ToDoList.repository;
import AlexeyPolyakov.ToDoList.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}