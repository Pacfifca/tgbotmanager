package Pacifica.tgbotek.service;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.entity.File;
import Pacifica.tgbotek.repository.EmployeeRepository;
import Pacifica.tgbotek.repository.FileRepository;
import org.apache.commons.io.monitor.FileEntry;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private EmployeeRepository employeeRepository;

    public FileService(FileRepository fileRepository, EmployeeRepository employeeRepository) {
        this.fileRepository = fileRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<File> getAllFiles(){
        return fileRepository.findAll();
    }

    public String  formatFilesList(List<File> files){
        if (files.isEmpty()){
            return ("список файлов пуст");
        }

        StringBuilder sb = new StringBuilder("Список файлов:\n\n");
        int counter = 1;
        for (File file : files){
            sb.append(String.format("%d\n\nID файла: %d\n",counter++,file.getId()));
            sb.append(String.format("Путь к файлу: %s\n",file.getFilePath()!= null ? file.getFilePath() : "не указан"));
            sb.append(String.format("ID Решения: %s\n",file.getDecisionId() != null ? file.getDecisionId() : "-"));
            String uploaderName = getEmployeeLastName(file.getUploadedBy());
            sb.append(String.format("Загрузил: %s\n", uploaderName));
            sb.append(String.format("Дата: %s\n",file.getUploadedAt() != null ? file.getUploadedAt():"неизвестно"));
            sb.append("\n");
        }
        return sb.toString();
    }
    private String getEmployeeLastName(Integer employeeId) {
        if (employeeId == null) {
            return "Неизвестно";
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
