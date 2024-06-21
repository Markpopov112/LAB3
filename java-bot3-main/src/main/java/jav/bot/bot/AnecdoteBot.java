package jav.bot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import jav.bot.entity.Joke;
import jav.bot.service.JokeService;

@Component
public class AnecdoteBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(AnecdoteBot.class);

    @Value("${telegram.bot.name}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    private final JokeService jokeService;

    @Autowired
    public AnecdoteBot(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update: {}", update); // Логирование всего объекта Update

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            log.info("Received message from chatId {}: {}", chatId, messageText); // Логирование текста сообщения и ID чата

            try {
                switch (messageText) {
                    case "/start":
                        log.info("Handling /start command from chatId {}", chatId);
                        sendResponse(chatId, "Привет! Я бот для анекдотов. Напиши /joke чтобы получить анекдот.");
                        break;
                    case "/joke":
                        log.info("Handling /joke command from chatId {}", chatId);
                        Joke randomJoke = jokeService.getRandomJoke(chatId);
                        String jokeText = randomJoke.getText() != null ? randomJoke.getText() : "Анекдотов пока нет.";
                        sendResponse(chatId, jokeText);
                        break;
                    default:
                        log.info("Received unknown command from chatId {}: {}", chatId, messageText);
                        sendResponse(chatId, "Извините, я не понимаю эту команду.");
                        break;
                }
            } catch (TelegramApiException e) {
                log.error("Exception while sending message to chatId: {}", chatId, e);
            }
        } else {
            log.info("Update does not contain a message with text"); // Логирование, если сообщение не содержит текста
        }
    }

    private void sendResponse(Long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        execute(message); // Отправка сообщения
        log.info("Sent response to chatId: {}", chatId); // Логирование отправки сообщения
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
