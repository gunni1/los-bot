package org.fun;


import java.util.*;
import java.util.stream.Collectors;

public class RaffleService
{

    private Map<String,List<String>> raffles;
    private Random random;


    public RaffleService(Random random)
    {
        this.raffles = new HashMap<>();
        this.random = random;

    }

    public void createRaffle(String title, List<String> participants)
    {
        raffles.put(title, participants);
    }

    public boolean hasRaffle(String title)
    {
        return this.raffles.containsKey(title);
    }

    public int getLotCount(String title)
    {
        Optional<List<String>> participants = Optional.ofNullable(this.raffles.get(title));
        return participants.map(part -> part.size()).orElse(0);
    }

    public Optional<String> raffle(String title, String raffelingUsersName)
    {
        Optional<List<String>> maybeParticipants = Optional.ofNullable(this.raffles.get(title));
        List<String> participants = maybeParticipants.orElse(new ArrayList<>());
        List<String> partWithoutRaffeling = participants.stream()
                .filter(part -> !part.equalsIgnoreCase(raffelingUsersName))
                .collect(Collectors.toList());
        if (!partWithoutRaffeling.isEmpty())
        {
            String result = partWithoutRaffeling.get(random.nextInt(partWithoutRaffeling.size()));
            List<String> remaining = participants.stream().filter(p -> !p.equalsIgnoreCase(result)).collect(Collectors.toList());
            raffles.replace(title, remaining);
            return Optional.of(result);
        }
        else
        {
            return Optional.empty();
        }
    }
}
