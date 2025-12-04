package Pacifica.tgbotek.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "decision_id")
    private Integer decisionId;

    @Column(name = "filepath")
    private String filePath;

    @Column(name = "uploaded_by")
    private Integer uploadedBy;

    @Column(name = "uploaded_at")
    private Instant uploadedAt;

    // Конструктор без параметров
    public File() {
    }

    // Конструктор со всеми параметрами
    public File(Integer id, Integer decisionId, String filePath,
                Integer uploadedBy, Instant uploadedAt) {
        this.id = id;
        this.decisionId = decisionId;
        this.filePath = filePath;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Integer uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getFileName() {
        if (filePath != null && filePath.contains("/")) {
            return filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return filePath;
    }


}