package Pacifica.tgbotek;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {


private final TelegramClient telegramClient;

    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient("8333041202:AAHQNO2U8ejaB-_c8P86XysLyuT2seGLEuA");
    }


    @Override
    public void consume(Update update) {

        System.out.printf(
                "Пришло сообщшение %s от %s%n",
                update.getMessage().getText(),
                update.getMessage().getChatId()
        );
        var chatId = update.getMessage().getChatId();
        SendMessage message = SendMessage.builder()
                .text("привет. твоя мама :" + update.getMessage().getText())
                .chatId(chatId)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
