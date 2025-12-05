package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Project;
import Pacifica.tgbotek.repository.ProjectRepository;
import Pacifica.tgbotek.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;

    public ProjectService(ProjectRepository projectRepository,
                          DepartmentRepository departmentRepository) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
    }



    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getActiveProjects() {
        return projectRepository.findByStatus("active");
    }

    public String formatProjectsList(List<Project> projects) {
        if (projects.isEmpty()) {
            return "Список проектов пуст";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        StringBuilder sb = new StringBuilder(" Проекты:\n\n");
        int counter = 1;

        for (Project project : projects) {
            sb.append(String.format("%d. %s\n", counter++, project.getName()));
            sb.append(String.format(" Описание: \n"));
            sb.append(String.format(" Сроки: %s - %s\n", project.getStartDate().format(formatter), project.getEndDate().format(formatter)));
            sb.append(String.format(" Статус: %s\n", project.getStatus() != null ? project.getStatus() : "не указан"));
            sb.append(String.format(" Бюджет: %s руб.\n", project.getBudget().toPlainString()));
            sb.append("\n");
        }

        return sb.toString();
    }
}