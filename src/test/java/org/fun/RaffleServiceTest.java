package org.fun;


import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class RaffleServiceTest
{
    private static final String TITLE = "TestLos";

    private Random random = new Random(9999999l);
    private RaffleService raffleService = new RaffleService(random);

    @Test
    public void raffle()
    {
        raffleService.createRaffle(TITLE, Arrays.asList("Hans", "Karl"));
        Optional<String> result = raffleService.raffle(TITLE, "Hans");
        MatcherAssert.assertThat(result.isPresent(), CoreMatchers.equalTo(true));
        MatcherAssert.assertThat(result.get(),CoreMatchers.equalTo("Karl"));
    }

    @Test
    public void raffleoften()
    {

        raffleService.createRaffle(TITLE, Arrays.asList("Hans", "Karl", "Peter"));
        Optional<String> result = raffleService.raffle(TITLE, "Hans");
        MatcherAssert.assertThat(result.isPresent(), CoreMatchers.equalTo(true));
        MatcherAssert.assertThat(result.get(),CoreMatchers.equalTo("Peter"));
        Optional<String> secondResult = raffleService.raffle(TITLE, "Hans");
        MatcherAssert.assertThat(secondResult.get(),CoreMatchers.equalTo("Karl"));
        Optional<String> another = raffleService.raffle(TITLE, "Hans");
        MatcherAssert.assertThat(another.isPresent(), CoreMatchers.equalTo(false));
    }

    @Test
    public void overrideRaffle()
    {
        raffleService.createRaffle(TITLE, Arrays.asList("Hans", "Karl", "Peter"));
        raffleService.createRaffle(TITLE, Arrays.asList("Enrico", "Jochen"));
        Optional<String> result = raffleService.raffle(TITLE, "Enrico");
        MatcherAssert.assertThat(result.get(),CoreMatchers.equalTo("Jochen"));
    }

    @Test
    public void getLotCount() {
        MatcherAssert.assertThat(raffleService.getLotCount(TITLE),CoreMatchers.equalTo(0));
        raffleService.createRaffle(TITLE, Arrays.asList("Hans", "Karl"));
        MatcherAssert.assertThat(raffleService.getLotCount(TITLE),CoreMatchers.equalTo(2));
        raffleService.raffle(TITLE,"someOneElse");
        MatcherAssert.assertThat(raffleService.getLotCount(TITLE),CoreMatchers.equalTo(1));
        raffleService.raffle(TITLE,"someOneElse");
        MatcherAssert.assertThat(raffleService.getLotCount(TITLE),CoreMatchers.equalTo(0));
    }
}
