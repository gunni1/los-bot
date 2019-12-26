package org.fun.command;

import org.fun.BotArgumentsParseUtils;
import org.fun.RaffleService;
import org.fun.ResponseSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Optional;


public class NewRaffleCommand extends BotCommand
{
    private static final String COMMAND_ID = "/neu";
    private static final String COMMAND_DESC = "Erstellt eine neue Auslosung: \n/neu <Titel> @Teilnehmer1 @Teilnehmer2 ...";
    private RaffleService raffleService;

    public NewRaffleCommand(RaffleService raffleService)
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
            String[] participants = Arrays.copyOfRange(arguments, 1, arguments.length);

            this.raffleService.createRaffle(maybeTitle.get(), Arrays.asList(participants));
            String message = "Neue Auslosung " + maybeTitle.get() +" erstellt." +
                    "\n Direktnachricht an den Bot mit /zieh " + maybeTitle.get() + " zieht einen Teilnehmer";
            responseSender.sendMessage(message);
        }
        else
        {
            responseSender.sendMessage("Bitte einen Titel für die Auslosung angeben.");
        }


    }
}
