package Pacifica.tgbotek.repository;


import Pacifica.tgbotek.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByNameContainingIgnoreCase (String Name);
    List<Department> findByManagerId (Integer ManagerId);

}
