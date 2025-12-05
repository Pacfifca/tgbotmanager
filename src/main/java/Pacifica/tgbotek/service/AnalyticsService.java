package Pacifica.tgbotek.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final EntityManager entityManager;

    public AnalyticsService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Статистика задач по месяцам и создателям
    public String getTaskStatisticsByMonth() {
        String sql = """
            SELECT 
                TO_CHAR(created_at, 'YYYY-MM') as month,
                e.last_name || ' ' || e.first_name as creator_name,
                COUNT(*) as task_count,
                SUM(CASE WHEN relevance = true THEN 1 ELSE 0 END) as active_tasks,
                SUM(CASE WHEN relevance = false THEN 1 ELSE 0 END) as inactive_tasks
            FROM tasks t
            LEFT JOIN employees e ON t.creator_id = e.id
            WHERE t.created_at >= CURRENT_DATE - INTERVAL '3 months'
            GROUP BY TO_CHAR(created_at, 'YYYY-MM'), e.last_name, e.first_name
            ORDER BY month DESC, task_count DESC
            LIMIT 20
            """;

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        return formatTaskStatistics(results);
    }


    // Топ сотрудников по активности
    public String getTopActiveEmployees() {
        String sql = """
            SELECT 
                e.id,
                e.last_name || ' ' || e.first_name as employee_name,
                e.employee_position,
                COUNT(DISTINCT t.id) as tasks_created,
                COUNT(DISTINCT f.id) as files_uploaded,
                (COUNT(DISTINCT t.id) + COUNT(DISTINCT f.id)) as total_activity
            FROM employees e
            LEFT JOIN tasks t ON e.id = t.creator_id
            LEFT JOIN files f ON e.id = f.uploaded_by
            GROUP BY e.id, e.last_name, e.first_name, e.employee_position
            HAVING (COUNT(DISTINCT t.id) + COUNT(DISTINCT f.id)) > 0
            ORDER BY total_activity DESC
            LIMIT 10
            """;

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        return formatTopEmployees(results);
    }

    // Анализ зависимостей задач
    public String getTaskDependenciesAnalysis() {
        String sql = """
            SELECT 
                td.dependency_type,
                COUNT(*) as dependency_count,
                COUNT(DISTINCT td.parent_task_id) as unique_parent_tasks,
                COUNT(DISTINCT td.child_task_id) as unique_child_tasks,
                AVG(CASE WHEN pt.relevance = true THEN 1.0 ELSE 0.0 END) * 100 as parent_active_percent,
                AVG(CASE WHEN ct.relevance = true THEN 1.0 ELSE 0.0 END) * 100 as child_active_percent
            FROM task_dependencies td
            LEFT JOIN tasks pt ON td.parent_task_id = pt.id
            LEFT JOIN tasks ct ON td.child_task_id = ct.id
            GROUP BY td.dependency_type
            ORDER BY dependency_count DESC
            """;

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        return formatDependencyAnalysis(results);
    }


    private String formatTaskStatistics(List<Object[]> results) {
        if (results.isEmpty()) {
            return " Нет данных за последние 3 месяца";
        }

        StringBuilder sb = new StringBuilder("📊 СТАТИСТИКА ЗАДАЧ ПО МЕСЯЦАМ\n\n");

        for (Object[] row : results) {
            sb.append("\n");
            sb.append(String.format(" Месяц: %s\n", row[0]));
            sb.append(String.format(" Создатель: %s\n", row[1] != null ? row[1] : "Неизвестно"));
            sb.append(String.format(" Всего задач: %s\n", row[2]));
            sb.append(String.format(" Активных: %s\n", row[3]));
            sb.append(String.format(" Неактивных: %s\n\n", row[4]));
        }

        return sb.toString();
    }


    private String formatTopEmployees(List<Object[]> results) {
        if (results.isEmpty()) {
            return "👥 Нет данных об активности сотрудников";
        }

        StringBuilder sb = new StringBuilder(" ТОП-10 АКТИВНЫХ СОТРУДНИКОВ\n\n");
        int counter = 1;

        for (Object[] row : results) {
            sb.append(String.format("%d. %s\n", counter++, row[1]));
            sb.append(String.format(" Должность: %s\n", row[2] != null ? row[2] : "Не указана"));
            sb.append(String.format(" Создано задач: %s\n", row[3]));
            sb.append(String.format(" Загружено файлов: %s\n", row[4]));
            sb.append(String.format(" Общая активность: %s\n\n", row[5]));
        }

        return sb.toString();
    }

    private String formatDependencyAnalysis(List<Object[]> results) {
        if (results.isEmpty()) {
            return "Нет данных о зависимостях задач";
        }

        StringBuilder sb = new StringBuilder("🔗 АНАЛИЗ ЗАВИСИМОСТЕЙ ЗАДАЧ\n\n");

        for (Object[] row : results) {
            sb.append("\n");
            sb.append(String.format(" Тип: %s\n", row[0]));
            sb.append(String.format(" Количество: %s\n", row[1]));
            sb.append(String.format(" Уникальных родительских: %s\n", row[2]));
            sb.append(String.format(" Уникальных дочерних: %s\n", row[3]));
            sb.append(String.format(" Активных родительских: %.1f%%\n", row[4]));
            sb.append(String.format(" Активных дочерних: %.1f%%\n\n", row[5]));
        }

        return sb.toString();
    }
}