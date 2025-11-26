package Pacifica.tgbotek;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {



    @Override
    public void consume(Update update) {
        System.out.printf(
                "Пришло сообщшение %s от %s%n",
                update.getMessage().getText(),
                update.getMessage().getChatId()
        );
    }
}
