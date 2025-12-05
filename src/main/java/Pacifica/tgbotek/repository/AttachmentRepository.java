package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    List<Attachment> findByTaskId(Integer taskId);
    List<Attachment> findByUploadedBy(Integer employeeId);
}