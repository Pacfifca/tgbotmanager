package Pacifica.tgbotek;
import Pacifica.tgbotek.service.*;
import Pacifica.tgbotek.service.DepartmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final EmployeeService employeeService;
    private final TaskService taskService;
    private final FileService fileService;
    private final DepartmentService departmentService;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final AttachmentService attachmentService;
    private final NotificationService notificationService;
    private final TimeLogService timeLogService;

    @Value("${telegram.bot.token}")
    private String botToken;

    private TelegramClient telegramClient;

    public UpdateConsumer(EmployeeService employeeService,
                          TaskService taskService,
                          FileService fileService,
                          DepartmentService departmentService,
                          ProjectService projectService,
                          CommentService commentService,
                          AttachmentService attachmentService,
                          NotificationService notificationService,
                          TimeLogService timeLogService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.fileService = fileService;
        this.departmentService = departmentService;
        this.projectService = projectService;
        this.commentService = commentService;
        this.attachmentService = attachmentService;
        this.notificationService = notificationService;
        this.timeLogService = timeLogService;
    }

    @PostConstruct
    private void init() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }


    @Override
    public void consume(Update update) {

        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start") || messageText.equals("начать")) {
                sendMainMenu(chatId);
                sendReplyKeyboard(chatId);
            } else {
                sendMessage(chatId, "Используйте кнопку ниже для открытия меню");
                sendReplyKeyboard(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void sendReplyKeyboard(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .build();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow("начать");
        keyboardRows.add(row1);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        message.setReplyMarkup(markup);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        var chatId = callbackQuery.getFrom().getId();
        var user = callbackQuery.getFrom();
        switch (data) {
            case "my_name" -> sendMyName(chatId, user);
            case "showEmployeeList" -> sendEmployeesList(chatId);
            case "showTasksList" -> sendTaskList(chatId);
            case "getFilesList" -> sendFilesList(chatId);
            case "showDepartments" -> sendDepartmentsList(chatId);
            case "showProjects" -> sendProjectsList(chatId);
            case "showComments" -> sendCommentsList(chatId);
            case "showAttachments" -> sendAttachmentsList(chatId);
            case "showNotifications" -> sendNotificationsList(chatId);
            case "showTimeLogs" -> sendTimeLogsList(chatId);
            default -> sendMessage(chatId, "Неизвестная комманда");
        }


    }

    private void sendMessage(Long chatId, String messageText) {
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendFilesList(Long chatId) {
        try {
            sendMessage(chatId, "присылаю список файлов...");
            var files = fileService.getAllFiles();
            String formattedList = fileService.formatFilesList(files);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка получения списка файлов" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendTaskList(Long chatId) {
        try {
            sendMessage(chatId, "присылаю список задач...");
            var tasks = taskService.getRelevantTasks();
            String formattedList = taskService.formatTasksList(tasks);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка получения списка задач" + e.getMessage());
            e.printStackTrace();
        }


    }

    private void sendEmployeesList(Long chatId) {
        try {
            sendMessage(chatId, "прислыаю список сотрудиков...");
            var employees = employeeService.getAllEmployees();
            String formattedList = employeeService.formatEmployeeList(employees);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка получения списка сотрудников");
            e.printStackTrace();

        }

    }

    private void sendDepartmentsList(Long chatId) {
        try {
            sendMessage(chatId, "Присылаю список отделов...");
            var departments = departmentService.getAllDepartments();
            String formattedList = departmentService.formatDepartmentList(departments);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка получения списка отделов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendProjectsList(Long chatId) {
        try {
            sendMessage(chatId, "Присылаю список проектов...");
            var projects = projectService.getAllProjects();
            String formattedList = projectService.formatProjectsList(projects);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, "Ошибка получения списка проектов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendCommentsList(Long chatId) {
        try {
            sendMessage(chatId, " Присылаю список комментариев...");
            var comments = commentService.getAllComments();
            String formattedList = commentService.formatCommentsList(comments);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, " Ошибка получения списка комментариев: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendAttachmentsList(Long chatId) {
        try {
            sendMessage(chatId, " Присылаю список вложений...");
            var attachments = attachmentService.getAllAttachments();
            String formattedList = attachmentService.formatAttachmentsList(attachments);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, " Ошибка получения списка вложений: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendNotificationsList(Long chatId) {
        try {
            sendMessage(chatId, " Присылаю список уведомлений...");
            var notifications = notificationService.getAllNotifications();
            String formattedList = notificationService.formatNotificationsList(notifications);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, " Ошибка получения списка уведомлений: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendTimeLogsList(Long chatId) {
        try {
            sendMessage(chatId, " Присылаю список учета времени...");
            // Для примера берем записи времени для сотрудника с ID=1
            var timeLogs = timeLogService.getAllTimeLogs();
            String formattedList = timeLogService.formatTimeLogsList(timeLogs);
            sendMessage(chatId, formattedList);
        } catch (Exception e) {
            sendMessage(chatId, " Ошибка получения списка учета времени: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void sendMyName(Long chatId, User user) {
        var text = "ПРОФИЛЬ:\n\nВаше имя: %s\nВаш ник: @%s"
                .formatted(
                        user.getFirstName() + " " + user.getLastName(),
                        user.getUserName()
                );
        sendMessage(chatId, text);
    }

    private void sendMainMenu(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text("Выберите действие:")
                .chatId(chatId)
                .build();

        var button1 = InlineKeyboardButton.builder()
                .text("отобразить сотрудников")
                .callbackData("showEmployeeList")
                .build();
        var button2 = InlineKeyboardButton.builder()
                .text("актуальные задачи")
                .callbackData("showTasksList")
                .build();
        var button3 = InlineKeyboardButton.builder()
                .text("файлы")
                .callbackData("getFilesList")
                .build();
        var button4 = InlineKeyboardButton.builder()
                .text("мой профиль")
                .callbackData("my_name")
                .build();
        var button5 = InlineKeyboardButton.builder()
                .text("Отделы")
                .callbackData("showDepartments")
                .build();
        var button6 = InlineKeyboardButton.builder()
                .text(" Проекты")
                .callbackData("showProjects")
                .build();
        var button7 = InlineKeyboardButton.builder()
                .text("Комментарии")
                .callbackData("showComments")
                .build();
        var button8 = InlineKeyboardButton.builder()
                .text(" Вложения")
                .callbackData("showAttachments")
                .build();
        var button9 = InlineKeyboardButton.builder()
                .text(" Уведомления")
                .callbackData("showNotifications")
                .build();
        var button10 = InlineKeyboardButton.builder()
                .text(" Учет времени")
                .callbackData("showTimeLogs")
                .build();
        var button11 = InlineKeyboardButton.builder()
                .text(" Зависимости")
                .callbackData("showTaskDependencies")
                .build();

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(button1),
                new InlineKeyboardRow(button2),
                new InlineKeyboardRow(button3),
                new InlineKeyboardRow(button4),
                new InlineKeyboardRow(button5),
                new InlineKeyboardRow(button6),
                new InlineKeyboardRow(button7),
                new InlineKeyboardRow(button8),
                new InlineKeyboardRow(button9),
                new InlineKeyboardRow(button10),
                new InlineKeyboardRow(button11)


        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboardRows);
        message.setReplyMarkup(markup);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        removeReplyKeyboard(chatId);
    }

    private void removeReplyKeyboard(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text(" ")  // можно оставить пустое сообщение
                .chatId(chatId)
                .replyMarkup(ReplyKeyboardRemove.builder()
                        .removeKeyboard(true)
                        .build())
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
