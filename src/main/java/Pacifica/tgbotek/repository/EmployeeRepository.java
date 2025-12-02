package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmployeePosition(String Position);
    List<Employee> findByLastName (String lastName);
}
