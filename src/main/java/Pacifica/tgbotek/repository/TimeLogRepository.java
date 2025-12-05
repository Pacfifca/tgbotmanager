package Pacifica.tgbotek.repository;

import Pacifica.tgbotek.entity.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Integer> {
    List<TimeLog> findByEmployeeId(Integer employeeId);
    List<TimeLog> findByTaskId(Integer taskId);
    List<TimeLog> findByEmployeeIdAndStartTimeBetween(Integer employeeId, LocalDateTime start, LocalDateTime end);
    List<TimeLog> findByEmployeeIdAndEndTimeIsNull(Integer employeeId);
}
    