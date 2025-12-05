package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskId(Integer taskId);
    List<Comment> findByEmployeeId(Integer employeeId);
}
