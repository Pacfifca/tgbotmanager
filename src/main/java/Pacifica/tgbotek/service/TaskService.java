package Pacifica.tgbotek.service;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.entity.Task;
import Pacifica.tgbotek.repository.EmployeeRepository;
import Pacifica.tgbotek.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }


    public List <Task> getRelevantTasks(){
        return taskRepository.findByRelevance(true);
    }

    public String formatTasksList (List<Task> tasks){
        if(tasks.isEmpty()){
            return "Список задач пуст";
        }

        StringBuilder sb = new StringBuilder("Список задач\n");
        int counter = 1;
        for (Task task : tasks){
            sb.append(String.format("%d. задача %s\n",counter++,task.getTitle()));
            sb.append(String.format("Описание %s\n",task.getDescription() != null ? task.getDescription():"Нет описания"));
            String creatorName = getEmployeeName(task.getCreatorId());
            sb.append(String.format("Создатель: %s\n", creatorName));
            sb.append(String.format("Срок %s\n ",task.getDueDate() != null ? task.getDueDate(): "не указан"));
            sb.append(String.format("Актуальность %s\n", task.getRelevance() != null && task.getRelevance() ? "актуальна" : "не актуальна" ));
            sb.append("\n");
        }
    return sb.toString();
    }
    private String getEmployeeName(Integer employeeId) {
        if (employeeId == null) {
            return "Не указан";
        }

        try {
            Long employeeIdLong = employeeId.longValue();
            Employee employee = employeeRepository.findById(employeeIdLong).orElse(null);
            if (employee != null) {
                return employee.getLastName() + " " + employee.getFirstName();
            } else {
                return "Сотрудник не найден (ID: " + employeeId + ")";
            }
        } catch (Exception e) {
            return "Ошибка получения данных (ID: " + employeeId + ")";
        }
    }
}
