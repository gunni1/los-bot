package org.fun.command;

import org.fun.BotArgumentsParseUtils;
import org.fun.RaffleService;
import org.fun.ResponseSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.Optional;


public class DrawRaffleCommand extends BotCommand
{
    private static final String COMMAND_ID = "/zieh";
    private static final String COMMAND_DESC = "Zieht einen Teilnehmer aus einer Auslosung \n/zieh <Titel>";
    private RaffleService raffleService;

    public DrawRaffleCommand(RaffleService raffleService)
    {
        super(COMMAND_ID, COMMAND_DESC);
        this.raffleService = raffleService;
    }



    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        ResponseSender responseSender = new ResponseSender(absSender, chat.getId(), COMMAND_ID);
        Optional<String> maybeTitle = BotArgumentsParseUtils.parseString(arguments, 0);
        if(!chat.isUserChat() || !maybeTitle.isPresent())
        {
            responseSender.sendMessage("Bitte /zieh mit Angabe des Titels als Direktnachricht an den Bot senden.");
            return;
        }

        if(raffleService.hasRaffle(maybeTitle.get()))
        {
            Optional<String> maybeResult = raffleService.raffle(maybeTitle.get(), user.getFirstName());
            if (!maybeResult.isPresent())
            {
                responseSender.sendMessage("Keine möglichen Lose mehr im Topf.");
            }
            else
            {
                String succesfulResult = "Für die Losung " + maybeTitle.get() + " hast du " + maybeResult.get() + " gezogen";
                responseSender.sendMessage(succesfulResult);
            }
        }
        else
        {
            responseSender.sendMessage("Es existiert keine aktive Auslosung mit dem Titel " + maybeTitle.get());
        }

    }
}
