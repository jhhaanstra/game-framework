package models;

public abstract class ClientCommands {


    /**
     * Deze functie wordt gebruikt om in te loggen op de server.
     * @param name
     * @return geeft het bericht terug van de server.
     * Deze parameter is de naam waarmee je inlogt op de server.
     */
    public static String login(String name) {
        Client.getInstance().send("login " + name);
        return getReturnMessage();
    }

    /**
     * Deze functie wordt gebruikt om uit te loggen op de server.
     * @return geeft het bericht terug van de server.
     */
    public static String logout() {
        Client.getInstance().send("logout");
        return getReturnMessage();
    }

    /**
     * Deze functie wordt gebruikt om je in te schrijven voor een game op de server.
     * @param game
     * @return geeft het bericht terug van de server.
     * Deze parameter geeft aan voor welke game je ingeschreven wordt op de server.
     */
    public static String subscribeGame(String game) {
        Client.getInstance().send("subscribe " + game);
        return getReturnMessage();
    }

    /**
     * Deze functie haalt de playerlist op van de server.
     * @return roept de getInfoPlayerList() aan.
     */
    public static String getPlayers() {
        Client.getInstance().send("get playerlist");
        return getInfoPlayerList();
    }

    /**
     * Stuurt een challenge accept naar de server.
     * @return geeft het info bericht van de server terug.
     */
    public static String acceptChallenge(String number) {
	Client.getInstance().send("challenge accept " + number);
	return getInfo();
    }

    /**
     * Stuurt een challenge naar een andere player via de server.
     * @return geeft het bericht van de server terug.
     */
    public static String challengePlayer(String player, String game) {
        Client.getInstance().send("challenge \"" + player.trim() + "\" \"" + game + "\"");
        return getReturnMessage();
    }

    /**
     * Stuurt een move naar de server wanneer je in een match zit.
     * @return geeft het bericht van de server terug.
     */
    public static String sendMove(int move) {
        Client.getInstance().send("move " + move);
        return getInfo();
    }

    /**
     * Luistert naar het bericht van de server (Error of OK messages).
     * @return geeft het bericht van de server terug.
     */
    public static String getReturnMessage() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Client.getInstance().getErorrs().isEmpty()) {
            return "true";
        }
        else return (String) Client.getInstance().getErorrs().pop();
    }

    /**
     * Luistert naar het bericht van de server (alle andere berichten).
     * @return geeft het bericht van de server terug.
     */
    public static String getInfo() {
        while (Client.getInstance().getInfo().size() == 0) {}
        String message = Client.getInstance().getInfo().pop();
        return message;

    }

    /**
     * Deze functie haalt het bericht van de ontvangen playerlist uit de queue.
     * @return geeft de playerlist terug.
     */
    public static String getInfoPlayerList() {
        while (Client.getInstance().getPlayerlist().size() == 0) {}
        String message = Client.getInstance().getPlayerlist().pop();
        return message;
    }

}
