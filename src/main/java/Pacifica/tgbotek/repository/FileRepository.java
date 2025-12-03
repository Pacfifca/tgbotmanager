package Pacifica.tgbotek.repository;
import Pacifica.tgbotek.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByUploadedBy(Long userId);
    List<File> findByDecisionId(Integer decisionId);
}
