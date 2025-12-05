package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Task;
import Pacifica.tgbotek.entity.TaskDependency;
import Pacifica.tgbotek.repository.TaskDependencyRepository;
import Pacifica.tgbotek.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDependencyService {

    private final TaskDependencyRepository taskDependencyRepository;
    private final TaskRepository taskRepository;

    public TaskDependencyService(TaskDependencyRepository taskDependencyRepository,
                                 TaskRepository taskRepository) {
        this.taskDependencyRepository = taskDependencyRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskDependency> getAllDependencies() {
        return taskDependencyRepository.findAll();
    }
    public TaskDependency addDependency(Integer parentTaskId, Integer childTaskId, String dependencyType) {
        TaskDependency dependency = new TaskDependency();
        dependency.setParentTaskId(parentTaskId);
        dependency.setChildTaskId(childTaskId);
        dependency.setDependencyType(dependencyType);
        return taskDependencyRepository.save(dependency);
    }

    public String formatDependenciesList(List<TaskDependency> dependencies) {
        if (dependencies.isEmpty()) {
            return "📊 Список зависимостей задач пуст";
        }

        StringBuilder sb = new StringBuilder("ЗАВИСИМОСТИ ЗАДАЧ:\n\n");
        int counter = 1;

        for (TaskDependency dep : dependencies) {
            sb.append("\n");
            sb.append(String.format("%d. Зависимость ID: %d\n", counter++, dep.getId()));
            String parentTaskName = getTaskTitle(dep.getParentTaskId());
            sb.append(String.format(" Родительская задача: %s\n", parentTaskName));
            String childTaskName = getTaskTitle(dep.getChildTaskId());
            sb.append(String.format(" Дочерняя задача: %s\n", childTaskName));
            sb.append(String.format(" Тип зависимости: %s\n",dep.getDependencyType() != null ? dep.getDependencyType() : "Не указан"));
            sb.append(String.format(" Описание: %s\n", getDependencyDescription(dep.getDependencyType())));

            sb.append("\n");
        }

        return sb.toString();
    }

    private String getTaskTitle(Integer taskId) {
        if (taskId == null) {
            return "Не указана";
        }

        try {
            Long taskIdLong = taskId.longValue();
            Task task = taskRepository.findById(taskIdLong).orElse(null);

            if (task != null) {
                return String.format("\"%s\" (ID: %d)", task.getTitle(), taskId);
            } else {
                return "Задача не найдена (ID: " + taskId + ")";
            }
        } catch (Exception e) {
            return "Ошибка получения данных (ID: " + taskId + ")";
        }
    }

    private String getDependencyDescription(String type) {
        if (type == null) return "Не указано";

        return switch (type.toLowerCase()) {
            case "finish_to_start" -> "Finish-to-Start: дочерняя задача начинается после завершения родительской";
            case "start_to_start" -> "Start-to-Start: обе задачи должны начаться одновременно";
            case "finish_to_finish" -> "Finish-to-Finish: обе задачи должны завершиться одновременно";
            case "start_to_finish" -> "Start-to-Finish: дочерняя завершается при старте родительской";
            default -> type;
        };
    }
}