package org.fun;

import org.fun.command.DrawRaffleCommand;
import org.fun.command.HelpCommand;
import org.fun.command.LotCountCommand;
import org.fun.command.NewRaffleCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RaffleBot extends TelegramLongPollingCommandBot
{
    private final RaffleService raffleService;
    private final String botToken;

    public RaffleBot(String botUsername, String botToken)
    {
        super(botUsername);
        this.botToken = botToken;
        this.raffleService = new RaffleService(new Random());

        register(new NewRaffleCommand(this.raffleService));
        register(new DrawRaffleCommand(this.raffleService));
        register(new LotCountCommand(this.raffleService));

        List<String> commandDescs = this.getRegisteredCommands().stream().map(command -> command.getDescription())
                .collect(Collectors.toList());
        register(new HelpCommand(commandDescs));
    }

    @Override
    public void processNonCommandUpdate(Update update)
    {
        BotLogger.info("no command", update.toString());
    }

    @Override
    public String getBotToken()
    {
        return botToken;
    }
}
