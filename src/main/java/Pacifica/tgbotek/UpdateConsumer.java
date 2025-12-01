package Pacifica.tgbotek;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {


private final TelegramClient telegramClient;

    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient("8333041202:AAHQNO2U8ejaB-_c8P86XysLyuT2seGLEuA");
    }


    @Override
    public void consume(Update update) {

        if (update.hasMessage()){
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")){
                sendMainMenu(chatId);
            } else {
                sendMessage(chatId,"Не понял");
            }
        } else if (update.hasCallbackQuery()){
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        var chatId = callbackQuery.getFrom().getId();
        var user = callbackQuery.getFrom();
        switch(data){
            case "my_name" -> sendMyName(chatId, user);
            case "showEmployeeList" -> sendEmployeesList(chatId);
            case "showTasksList" -> sendTaskList(chatId);
            case "getFilesList" -> sendFilesList(chatId);
            default -> sendMessage(chatId, "Неизвестная комманда");
        }



    }

    private void sendMessage(Long chatId, String messageText){
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
        sendMessage(chatId, "присылаю список файлов...");

    }

    private void sendTaskList(Long chatId) {
        sendMessage(chatId, "присылаю список задач...");

    }

    private void sendEmployeesList(Long chatId) {
        sendMessage(chatId,"прислыаю список сотрудиков...");

    }

    private void sendMyName(Long chatId, User user) {

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

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(button1),
                new InlineKeyboardRow(button2),
                new InlineKeyboardRow(button3),
                new InlineKeyboardRow(button4)
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboardRows);

        message.setReplyMarkup(markup);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
