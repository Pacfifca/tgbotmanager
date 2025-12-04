package Pacifica.tgbotek.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @Column(name = "creator_id")
    private Integer creatorId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "due_date",nullable = false)
    private String dueDate;

    private Boolean relevance;

    public Task() {
    }

    // Конструктор со всеми параметрами
    public Task(Integer id, String title, String description, Integer creatorId,
                Instant createdAt, String dueDate, Boolean relevance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.relevance = relevance;
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getRelevance() {
        return relevance;
    }

    public void setRelevance(Boolean relevance) {
        this.relevance = relevance;
    }
}
