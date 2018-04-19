package controllers.simpleGame;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.shape.Circle;
import controllers.GameStartController;
import models.Client;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.*;
import lib.Parser;
import views.GameView;
import javafx.scene.control.Alert.AlertType;
import java.util.*;

/**
 * Generieke gameController waar andere spellen zich op kunnen baseren. Deze klasse
 * legt de basis voor het framework.
 */
public abstract class SimpleGameController {
    protected Game gameModel;
    protected GameView gameView;
    protected Stage primaryStage;
    // Een array die bijhoudt welke plaatsen bezet zijn, wordt gebruikt om minder checks uit te voeren aan het begin van de game
    protected List<Integer> occupied = new ArrayList<>();
    protected Circle[] pieces;
    protected static Color startColor;
    protected static Color opponentColor;

    /**
     * Constructor voor de generieke game klasse. Stelt de game model in op basis van de servercommunicatie
     * @param model Model van de game die wordt gebruikt
     * @param primaryStage De Stage voor javaFX waarin de views worden geladen.
     * @param gameView De view die het speel weergeeft.
     * @param info Een HashMap met info over het spel dat van de server is ontvangen.
     */
    public SimpleGameController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        gameModel = model;
        gameModel.setOpponent((String) info.get("OPPONENT"));
        gameView.setOpponent((String) info.get("OPPONENT"));
        pieces = new Circle[gameModel.getGridWidth() * gameModel.getGridHeight()];

        if (info.get("PLAYERTOMOVE").equals(Player.getInstance().getName())) gameModel.setYourTurn(true);
        else {
            gameModel.setYourTurn(false);
            gameView.setTurn(gameModel.getOpponent());
        }
        this.gameView = gameView;
        this.primaryStage = primaryStage;

        if(!gameModel.isYourTurn()) {
            startColor = Color.WHITE;
            opponentColor = Color.BLACK;
        } else {
            startColor = Color.BLACK;
            opponentColor = Color.WHITE;
        }
        gameView.setPlayerColor(startColor);
        gameView.setOpponentColor(opponentColor);
        primaryStage.setScene(this.gameView);
    }

    /**
     * Voegt een gele highlight toe aan een steen en de functionaliteit om een beurt te doen zodra je erop klikt.
     *
     * @param i index van de steen in de model.gamestate- en pieces array
     */
    protected void setOnClick(int i) {
        Circle r = pieces[i];
        r.setFill(Color.YELLOW);
        r.setOnMouseClicked(e -> {
            try {
                ClientCommands.sendMove(i);
                gameModel.updatePlayField(i);
                gameModel.incrementTurn();
                gameView.setTurn(gameModel.getOpponent());
                gameModel.setYourTurn(false);
                occupied.add(i);
                updateGame();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            r.setDisable(true);
        });
    }

    /**
     * Genereert een nieuwe GridPane van de hoogte en breedte van het spel. Geeft per vakje een groene achtergrond
     * met een cirkel bovenop. De kleur hiervan is op basis van de playfield parameter.
     *
     * @param playField array die de stand van het spel representeert
     *                  1 = speler
     *                  2 = tegenstander
     *                  0 = niet bezet
     * @return nieuw gegenereerde GridPane op basis van de meegegeven playField.
     */
    protected GridPane generateGrid(int[] playField) {
        GridPane grid = new GridPane();
        for (int y = 0; y < gameModel.getGridHeight(); y++) {
            for (int x = 0; x < gameModel.getGridWidth(); x++) {
                int index = (y * gameModel.getGridWidth()) + x;
                Circle c = new Circle();
                c.setRadius(20);
                pieces[(y * gameModel.getGridWidth()) + x] = c;
                Rectangle r = new Rectangle(50, 50);
                r.setFill(Color.GREEN);
                switch (playField[index]) {
                    case 0:
                        c.setFill(Color.GREEN);
                        break;
                    case 1:
                        c.setFill(startColor);
                        break;
                    case 2:
                        c.setFill(opponentColor);
                        break;
                }
                r.setStroke(Color.WHITE);
                grid.add(r, x, y);
                grid.add(c, x, y);
                GridPane.setHalignment(c, HPos.CENTER);
                GridPane.setValignment(c, VPos.CENTER);
            }
        }
        return grid;
    }

    /**
     * Vernieuw het grid.
     */
    protected void updateGame() {
        gameView.setGrid(generateGrid(gameModel.getPlayField()));
    }

    /**
     * Genereer een Alert met daarin of je gewonnen, verloren of gelijkspel hebt. Stuur de gebruiker
     * vervolgens door naar de GameStartController als hij op ok heeft geklikt.
     */
    protected void getScore() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            String response = Client.getInstance().getScore().pop();
            if (response.contains("WIN"))
                alert.setHeaderText("Congratulations " + Player.getInstance().getName() + ", you won!!");
            else if (response.contains("DRAW"))
                alert.setHeaderText("The game between you and " + gameModel.getOpponent() + " ended in a draw.");
            else
                alert.setHeaderText("Sorry " + Player.getInstance().getName() + ", you lost..");
            alert.setTitle("Score");
            alert.setContentText("You can back to the game select room.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                new GameStartController(primaryStage);
        });
    }

    /**
     * Controleer of de meegegeven zet een legale zet is
     * @param index index van de zet die is meegegeven. Deze is te vergelijken met het playfield van de
     *              game model.
     * @return Boolean die aangeeft of de move legaal is of niet.
     */
    protected boolean legalMove(int index) {
        return gameModel.isYourTurn() && gameModel.getPlayField()[index] == 0;
    }

    /**
     * Update het speelveld
     * @param i Index van de zet in het speelveld
     */
    protected void updateBoard(int i) {
        gameModel.updatePlayField(i);
        gameModel.incrementTurn();
    }

    /**
     * Listener runnable die kijkt naar de turn en score Queues in de Client en deze berichten verwerkt naar de Game.
     */
    class MoveListener implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while(running) {

                // Luistert naar de turn queue
                if (Client.getInstance().getTurn().size() > 0) {
                    // Een bericht in de turn queue geft nog niet gelijk aan van wie de beurt is.
                    // Dit is alleen het geval zodra er geen DETAILS-tag in het bericht zit.
                    if (Client.getInstance().getTurn().getFirst().contains("DETAILS")) {
                        HashMap info = Parser.parse(Client.getInstance().getTurn());
                        if (!info.get("PLAYER").equals(Player.getInstance().getName())) {
                            Platform.runLater(() -> {
                                int index = Integer.valueOf((String) info.get("MOVE"));
                                updateBoard(index);
                            });
                        }
                    } else {
                        // Geef de speler weer de beurt. Zodra hij een zet heeft gedaan en nog een zet moet doen,
                        // komt er weer een nieuwe binnen die wederom aangeeft dat het de speler zijn beurt is.
                        Client.getInstance().getTurn().removeFirst();
                        gameModel.setYourTurn(true);
                        Platform.runLater(() -> {
                            gameView.setTurn(Player.getInstance().getName());
                            updateGame();
                        });
                    }
                }

                // Luistert naar of er een uitslag is bepaald in de game
                if (Client.getInstance().getScore().size() > 0) {
                    running = false;
                    getScore();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}

            }

        }
    }

    public static Color getStartColor(){
        return startColor;
    }

    public static Color getOpponentColor(){
        return opponentColor;
    }

}
