package ru.halcyon.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.halcyon.telegrambot.service.UpdateProcessor;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String username;
    private final UpdateProcessor updateProcessor;

    public TelegramBot(
            @Value("${bot.username}") String username,
            @Value("${bot.token}") String token,
            @Autowired UpdateProcessor updateProcessor
    ) {
        super(token);
        this.username = username;
        this.updateProcessor = updateProcessor;
        updateProcessor.setBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            updateProcessor.processMessage(update.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    public void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }
    }
}
