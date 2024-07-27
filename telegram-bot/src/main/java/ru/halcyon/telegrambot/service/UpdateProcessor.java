package ru.halcyon.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.halcyon.telegrambot.bot.BotAnswers;
import ru.halcyon.telegrambot.bot.TelegramBot;
import ru.halcyon.telegrambot.client.GameNewsClient;
import ru.halcyon.telegrambot.model.User;
import ru.halcyon.telegrambot.support.GameNews;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateProcessor {
    private final UserService userService;
    private final GameNewsClient gameNewsClient;
    private TelegramBot bot;

    public void processMessage(Message message) {
        if (message.getText().equals("/start")) {
            processStartCommand(message);
        } else if (message.getText().startsWith("/news")) {
            try {
                int limit = Integer.parseInt(message.getText().split(" ")[1]);
                processGetNewsCommand(limit, message.getChatId());
            } catch (Exception e) {
                processIncorrectCommand(message.getChatId());
            }
        } else if (message.getText().startsWith("/search ")) {
            try {
                String title = message.getText().split(" ")[1];
                processSearchCommand(title, message.getChatId());
            } catch (Exception e) {
                processIncorrectCommand(message.getChatId());
            }
        }
    }

    @Async
    public void sendRecentNews(List<GameNews> gameNews) {
        for (User user: userService.findAll()) {
            sendNews(gameNews, user.getChatId());
        }
    }

    private void sendNews(List<GameNews> gameNews, long chatId) {
        for (GameNews news: gameNews) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            String tags = "\n\n";

            if (news.getTags() != null) {
                StringBuilder tagsBuilder = new StringBuilder();

                news.getTags().forEach(el -> tagsBuilder.append(el).append(" "));
                tags = String.format("\n\nТеги: %s\n\n", tagsBuilder);
            }

            sendMessage.setText(String.format(
                    BotAnswers.NEWS_MESSAGE.answer(),
                    news.getTitle(),
                    new SimpleDateFormat("yyyy-MM-dd").format(news.getCreatedAt().getTime()),
                    tags,
                    news.getSource()));

            bot.executeMessage(sendMessage);
        }
    }

    private void processStartCommand(Message message) {
        long chatId = message.getChatId();
        userService.registerUser(message.getFrom().getUserName(), chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText(BotAnswers.START_MESSAGE.answer());

        bot.executeMessage(sendMessage);
    }

    private void processGetNewsCommand(int limit, long chatId) {
        sendNews(gameNewsClient.getGameNews(limit), chatId);
    }

    private void processIncorrectCommand(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Неккоректная команда.");

        bot.executeMessage(sendMessage);
    }

    private void processSearchCommand(String title, long chatId) {
        sendNews(gameNewsClient.searchNews(title), chatId);
    }

    private void processWithoutCommandMessage() {

    }

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }
}
