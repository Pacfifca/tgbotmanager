package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.TimeLog;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.entity.Task;
import Pacifica.tgbotek.repository.TimeLogRepository;
import Pacifica.tgbotek.repository.EmployeeRepository;
import Pacifica.tgbotek.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public TimeLogService(TimeLogRepository timeLogRepository,
                          EmployeeRepository employeeRepository,
                          TaskRepository taskRepository) {
        this.timeLogRepository = timeLogRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public List<TimeLog> getTimeLogsByEmployee(Integer employeeId) {
        return timeLogRepository.findByEmployeeId(employeeId);
    }

    public List<TimeLog> getAllTimeLogs() {
        return timeLogRepository.findAll();
    }

    public String formatTimeLogsList(List<TimeLog> timeLogs) {
        if(timeLogs.isEmpty()){
            return "Нет записей учета времени";
        }

        StringBuilder sb = new StringBuilder("Учет времени:\n\n");
        int counter = 1;

        for (TimeLog log : timeLogs){
            sb.append(String.format("%d. ", counter++));
            String employeeName = getEmployeeName(log.getEmployeeId());
            sb.append(String.format("Сотрудник: %s\n", employeeName));
            String taskTitle = getTaskTitle(log.getTaskId());
            sb.append(String.format("Задача: %s\n", taskTitle));
            sb.append(String.format("Начало: %s\n", log.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
            sb.append(String.format("Конец: %s\n",log.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
            sb.append(String.format("Описание: %s\n", log.getDescription()));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getEmployeeName(Integer employeeId) {
        if (employeeId == null) {
            return "Неизвестный сотрудник";
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
            return "ID: " + employeeId;
        }
    }

    private String getTaskTitle(Integer taskId) {
        if (taskId == null) {
            return "Не указана";
        }

        try {
            Long taskIdLong = taskId.longValue();
            Task task = taskRepository.findById(taskIdLong).orElse(null);
            if (task != null) {
                return task.getTitle();
            } else {
                return "Задача (ID: " + taskId + ")";
            }
        } catch (Exception e) {
            return "ID: " + taskId;
        }
    }
}