package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByDepartmentId(Integer departmentId);
    List<Project> findByStatus(String status);
    List<Project> findByNameContainingIgnoreCase(String name);
}