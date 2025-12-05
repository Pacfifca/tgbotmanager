package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByEmployeeId(Integer employeeId);
    List<Notification> findByEmployeeIdAndIsRead(Integer employeeId, Boolean isRead);
    List<Notification> findByType(String type);
}