package dev.leeshuyun.TelegramBot.BotConfig;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * LifeGuildBot
 */
@Component
public class LifeGuildBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onUpdateReceived'");
    }

    @Override
    public String getBotUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBotUsername'");
    }
    // Bot body.
}