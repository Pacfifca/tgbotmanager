package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByRelevance(Boolean relevance);
    List<Task> findByCreatorId(Long employeeId);
}