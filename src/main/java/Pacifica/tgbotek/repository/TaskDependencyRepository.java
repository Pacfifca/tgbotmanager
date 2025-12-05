package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {
    List<TaskDependency> findByParentTaskId(Integer parentTaskId);
    List<TaskDependency> findByChildTaskId(Integer childTaskId);
    List<TaskDependency> findByDependencyType(String dependencyType);

    @Query(value = """
        SELECT 
            td.id,
            td.parent_task_id,
            td.child_task_id,
            td.dependency_type,
            pt.title as parent_title,
            ct.title as child_title,
            pt.relevance as parent_relevance,
            ct.relevance as child_relevance
        FROM task_dependencies td
        LEFT JOIN tasks pt ON td.parent_task_id = pt.id
        LEFT JOIN tasks ct ON td.child_task_id = ct.id
        WHERE td.dependency_type = :depType
        ORDER BY td.id
        """, nativeQuery = true)
    List<Object[]> findDependenciesWithTaskInfo(@Param("depType") String dependencyType);
}