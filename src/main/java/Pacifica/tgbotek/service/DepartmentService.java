package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Department;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.repository.DepartmentRepository;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository){
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List <Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public String formatDepartmentList(List <Department> departments){
        if (departments.isEmpty()){
            return "Список отделов пуст";
        }
        StringBuilder sb = new StringBuilder("Список отделов:\n");
        int counter = 1;
        for (Department department : departments) {
            sb.append(String.format("Отдел %d %s \n", counter++, department.getName() != null ? department.getName() : ""));
            String managerName = getEmployeeLastName(department.getManagerId());
            sb.append(String.format("Менеджер отдела: %s\n", managerName));
            sb.append(String.format("Бюджет отдела: %d руб .\n", department.getBudget() != null ? department.getBudget() : "Не указана"));
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

