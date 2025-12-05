package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Attachment;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.repository.AttachmentRepository;
import Pacifica.tgbotek.repository.EmployeeRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final EmployeeRepository employeeRepository;

    public AttachmentService(AttachmentRepository attachmentRepository,
                             EmployeeRepository employeeRepository) {
        this.attachmentRepository = attachmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    public List<Attachment> getAttachmentsByTaskId(Integer taskId) {
        return attachmentRepository.findByTaskId(taskId);
    }

    public String formatAttachmentsList(List<Attachment> attachments) {
        if(attachments.isEmpty()){
            return "Нет вложений";
        }

        StringBuilder sb = new StringBuilder("Вложения:\n\n");
        int counter = 1;

        for (Attachment attachment : attachments){
            sb.append(String.format("%d. Файл: %s\n", counter++,attachment.getFileName() != null ? attachment.getFileName() : "без названия"));
            String uploadedByName = getEmployeeName(attachment.getUploadedBy());
            sb.append(String.format("Загрузил: %s\n", uploadedByName));
            sb.append(String.format("Дата: %s\n", attachment.getUploadedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getEmployeeName(Integer employeeId) {
        if (employeeId == null) {
            return "Неизвестно";
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
}