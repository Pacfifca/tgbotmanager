package Pacifica.tgbotek.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "decision_id")
    private Integer decisionId;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @Column(name = "uploaded_date")
    private Instant uploadedAt;

    // Конструктор без параметров
    public File() {
    }

    // Конструктор со всеми параметрами
    public File(Long id, Integer decisionId, String filePath,
                      Long uploadedBy, Instant uploadedAt) {
        this.id = id;
        this.decisionId = decisionId;
        this.filePath = filePath;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(Integer decisionId) {
        this.decisionId = decisionId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Long uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}