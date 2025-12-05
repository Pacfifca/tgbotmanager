package Pacifica.tgbotek.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task_dependencies")
public class TaskDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_task_id")
    private Integer parentTaskId;

    @Column(name = "child_task_id")
    private Integer childTaskId;

    @Column(name = "dependency_type")
    private String dependencyType;

    public TaskDependency() {
    }

    public TaskDependency(Long id, Integer parentTaskId, Integer childTaskId, String dependencyType) {
        this.id = id;
        this.parentTaskId = parentTaskId;
        this.childTaskId = childTaskId;
        this.dependencyType = dependencyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Integer parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Integer getChildTaskId() {
        return childTaskId;
    }

    public void setChildTaskId(Integer childTaskId) {
        this.childTaskId = childTaskId;
    }

    public String getDependencyType() {
        return dependencyType;
    }

    public void setDependencyType(String dependencyType) {
        this.dependencyType = dependencyType;
    }
}