package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Comment;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.entity.Task;
import Pacifica.tgbotek.repository.CommentRepository;
import Pacifica.tgbotek.repository.EmployeeRepository;
import Pacifica.tgbotek.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository,
                          EmployeeRepository employeeRepository,
                          TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public List<Comment> getCommentsByTaskId(Integer taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public String formatCommentsList(List<Comment> comments) {
        if(comments.isEmpty()){
            return "Нет комментариев";
        }

        StringBuilder sb = new StringBuilder("Комментарии:\n\n");
        int counter = 1;
        for (Comment comment : comments){
            sb.append(String.format("%d. ", counter++));
            String authorName = getEmployeeName(comment.getEmployeeId());
            sb.append(String.format("%s\n", authorName));
            sb.append(String.format("Комментарий: %s\n", comment.getContent()));
            sb.append(String.format("Дата: %s\n", comment.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getEmployeeName(Integer employeeId) {
        if (employeeId == null) {
            return "Неизвестный автор";
        }

        try {
            Long employeeIdLong = employeeId.longValue();
            Employee employee = employeeRepository.findById(employeeIdLong).orElse(null);
            if (employee != null) {
                return employee.getFirstName() + " " + employee.getLastName();
            } else {
                return "Сотрудник (ID: " + employeeId + ")";
            }
        } catch (Exception e) {
            return "Автор (ID: " + employeeId + ")";
        }
    }
}