package org.fun.command;

import org.fun.BotArgumentsParseUtils;
import org.fun.RaffleService;
import org.fun.ResponseSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.Optional;


public class LotCountCommand extends BotCommand
{
    private static final String COMMAND_ID = "/lose";
    private static final String COMMAND_DESC = "Liefert die Anzahl der noch zu nicht vergebenen Lose: \n/lose <Titel>";
    private RaffleService raffleService;

    public LotCountCommand(RaffleService raffleService)
    {
        super(COMMAND_ID, COMMAND_DESC);
        this.raffleService = raffleService;
    }



    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        ResponseSender responseSender = new ResponseSender(absSender, chat.getId(), COMMAND_ID);
        Optional<String> maybeTitle = BotArgumentsParseUtils.parseString(arguments, 0);
        if (maybeTitle.isPresent())
        {
            int lotCount = raffleService.getLotCount(maybeTitle.get());

            String message = "Im Topf für " + maybeTitle.get() + " sind noch " + lotCount + " Lose zu vergeben.";
            responseSender.sendMessage(message);
        }
        else
        {
            responseSender.sendMessage("Bitte einen Titel für die Auslosung angeben.");
        }


    }
}
