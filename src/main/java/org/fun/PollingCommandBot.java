package org.fun;


import org.jboss.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class PollingCommandBot extends TelegramLongPollingBot
{
    private final String botToken;
    private final String botUserName;
    private static final Logger LOGGER = Logger.getLogger("BotBean");
    private RaffleService raffleService;

    public PollingCommandBot(String botUsername, String botToken)
    {
        this.botToken = botToken;
        this.botUserName = botUsername;
        this.raffleService = new RaffleService(new Random());
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().isCommand())
        {
            if(update.getMessage().getText().startsWith("/neu"))
            {
                List<User> participants = update.getMessage().getEntities().stream()
                        .filter(entity -> entity.getType().equalsIgnoreCase("text_mention"))
                        .map(entity -> entity.getUser())
                        .collect(Collectors.toList());

                //raffleService.createRaffle(update.getMessage().getChatId(), participants);
                //Nachricht, dass die Beteiligten /zieh machen sollen
            }
            else if(update.getMessage().getText().startsWith("/zieh"))
            {
                //
            }



            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        return this.botUserName;
    }

    @Override
    public String getBotToken()
    {
        return this.botToken;
    }
}
