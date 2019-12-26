package org.fun.command;

import org.fun.ResponseSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class HelpCommand extends BotCommand
{
    private static final String COMMAND_ID = "/help";
    private static final String COMMAND_DESC = "Listet alle Commands";

    private List<String> commandDescs;

    public HelpCommand(List<String> commandDescs)
    {
        super(COMMAND_ID, COMMAND_DESC);
        this.commandDescs = commandDescs;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        StringBuilder helpMsg = new StringBuilder();
        helpMsg.append("Unterstützte commands: " + "\n");
        commandDescs.stream().forEach(desc -> helpMsg.append(desc + "\n"));

        ResponseSender responseSender = new ResponseSender(absSender, chat.getId(), COMMAND_ID);
        responseSender.sendMessage(helpMsg.toString());
    }
}
