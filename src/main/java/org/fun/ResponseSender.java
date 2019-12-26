package org.fun;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;

public class ResponseSender
{
    private final AbsSender absSender;
    private final Long chatId;
    private final String commandId;
    private static final Logger LOGGER = Logger.getLogger("ResponseSender");

    public ResponseSender(AbsSender absSender, Long chatId, String commandId)
    {
        this.absSender = absSender;
        this.chatId = chatId;
        this.commandId = commandId;
    }

    /**
     * Sendet ab den Absender die übergebene Message. Tritt dabei ein Fehler auf, wird dieser geloggt.
     */
    public void sendMessage(String message)
    {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try{
            absSender.execute(sendMessage);
        }catch (TelegramApiException e) {
            LOGGER.info("failed to send command response to chat: " + chatId);
            LOGGER.error(e);
        }
    }

    public void sendPhoto(byte[] imageBytes, String caption)
    {
        SendPhoto photo = new SendPhoto().setPhoto(caption, new ByteArrayInputStream(imageBytes)).setChatId(chatId);
        try
        {
            absSender.execute(photo);
        }catch (TelegramApiException e){
            LOGGER.info("failed to send photo response to chat: " + chatId);
            LOGGER.error( e);
        }

    }
}
