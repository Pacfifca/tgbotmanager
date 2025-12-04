package Pacifica.tgbotek.service;
import Pacifica.tgbotek.entity.Employee;
import Pacifica.tgbotek.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public String formatEmployeeList(List<Employee> employees) {
        if (employees.isEmpty()) {
            return "Список сотрудников пуст";
        }

        StringBuilder sb = new StringBuilder("Список сотрудников:\n");
        int counter = 1;
        for (Employee emp : employees) {
            sb.append(String.format("%d. Сотрудник %s %s\n", counter++, emp.getFirstName(), emp.getLastName() != null ? emp.getLastName() : ""));
            sb.append(String.format("Должность: %s\n", emp.getEmployeePosition() != null ? emp.getEmployeePosition() : "Не указана"));
            sb.append(String.format("Паспортные данные сотрудника: %s %s\n", emp.getPassportSeries() != null ? emp.getPassportSeries() : "—", emp.getPassportNumber() != null ? emp.getPassportNumber() : "—"));
            sb.append(String.format("Зарплата: %s руб .\n", emp.getSalary() != null ? emp.getSalary() : "Не указана"));
            sb.append("\n");
        }
        return sb.toString();
    }
}
