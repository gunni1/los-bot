# Konfiguration

Properties für java mit Punkten getrennt, als Umgebungsvariablen mit Unterstrich


| PROPERTY  | Beschreibung |
|---|---|
| BOT_TOKEN | Token des Telegram Bots|

# Commands
- `/new <Titel> @Teilnehmer1 @Teilnehmer2 ...` startet eine neue Ziehung unter den angegebenen Teilnehmern mit dem angegebenen Titel.
- `/zieh` Zieht aus dem Lostopf einen der Beteiligten Teilnehmer 
  - Der Ziehende muss in der Liste der Teilnehmer sein
  - Der Ziehende kann sich nicht selbst ziehen
  - Es kann genau einmal gezogen werden
  - Es muss im Gruppenchat eine aktive Ziehung geben
  - Der Ziehende erhält den gezogenen als Privatnachricht