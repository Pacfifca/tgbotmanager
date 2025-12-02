package Pacifica.tgbotek.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "decision_id")
    private Integer decisionId;
    @Column(name = "file_path")
    private String filepath;
    @Column(name = "uploaded_by")
    private Long uploadedBy;
    @Column(name = "uploaded_date")
    private Instant uploadedAt;

}
