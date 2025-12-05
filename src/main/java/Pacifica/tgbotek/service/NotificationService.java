package Pacifica.tgbotek.service;

import Pacifica.tgbotek.entity.Notification;
import Pacifica.tgbotek.repository.NotificationRepository;
import Pacifica.tgbotek.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               EmployeeRepository employeeRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll(); // или для конкретного сотрудника
    }

    public List<Notification> getUnreadNotifications(Integer employeeId) {
        return notificationRepository.findByEmployeeIdAndIsRead(employeeId, false);
    }

    public String formatNotificationsList(List<Notification> notifications) {
        if (notifications.isEmpty()) {
            return " Нет уведомлений";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        StringBuilder sb = new StringBuilder(" Уведомления:\n\n");
        int counter = 1;

        for (Notification notification : notifications) {
            String icon = "•";
            if (notification.getType() != null) {
                switch(notification.getType()) {
                    case "task_assigned": icon = "📌"; break;
                    case "overdue": icon = "⚠️"; break;
                    case "reminder": icon = "⏰"; break;
                    case "system": icon = "⚙️"; break;
                    default: icon = "📨";
                }
            }

            sb.append(String.format("%d. %s %s\n", counter++, icon, notification.getMessage()));
            sb.append(String.format(" %s\n", notification.getCreatedAt().format(formatter)));
            if (notification.getIsRead() != null && !notification.getIsRead()) {
                sb.append(" Не прочитано\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}