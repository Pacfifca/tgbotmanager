package Pacifica.tgbotek;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
public class tgmain implements SpringLongPollingBot {

    private final UpdateConsumer UpdateConsumer;


    public tgmain(UpdateConsumer updateConsumer) {
        UpdateConsumer = updateConsumer;
    }

    @Override
    public String getBotToken() {
        return "8333041202:AAHQNO2U8ejaB-_c8P86XysLyuT2seGLEuA";
    }



    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return UpdateConsumer;
    }
}
