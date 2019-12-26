package org.fun;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.enterprise.event.Observes;

@ApplicationScoped
public class BotLifecycleBean
{

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");
    private static final String BOT_USERNAME = "GunnisLosBot";

    @ConfigProperty(name = "bot.token")
    String botToken;

    void onStart(@Observes StartupEvent ev) throws TelegramApiRequestException
    {
        LOGGER.info("The application is starting. Try to connect to telegram");
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        RaffleBot raffleBot = new RaffleBot(BOT_USERNAME, botToken);
        telegramBotsApi.registerBot(raffleBot);

        LOGGER.info("RaffleBot registered.");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }
}