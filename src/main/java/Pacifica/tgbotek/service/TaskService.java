package Pacifica.tgbotek.service;
import Pacifica.tgbotek.entity.Task;
import Pacifica.tgbotek.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

        StringBuilder sb = new StringBuilder("Список задач");
        int counter = 1;
        for (Task task : tasks){
            sb.append(String.format("%d. задача %s\n",counter++,task.getTitle()));
            sb.append(String.format("Описание %s\n",task.getDescription() != null ? task.getDescription():"Нет описания"));
            sb.append(String.format("ID создателя %s\n",task.getCreatorId() !=null ? task.getCreatorId():"-"));
            sb.append(String.format("Срок %s\n ",task.getDueDate() != null ? task.getDueDate(): "не указан"));
            sb.append(String.format("%s Актуальность %s\n", task.getRelevance() != null && task.getRelevance() ? "актуальна" : "не актуальна" ));
            sb.append("\n");
        }
    return sb.toString();
    }

}
