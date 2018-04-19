package controllers.simpleGame;

import java.util.*;
import controllers.AIController;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.*;
import views.GameView;

/**
 * De reversiController definieert de regels die boven op de regels van de generieke SimpleGameController op
 * moeten komen voor reversi.
 */
public class ReversiController extends SimpleGameController implements GameController {
    //private Game gameModel;
    int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9}; // Alle richtingen binnen het 1 dimensionale grid
    // Wordt gebruikt om de randen te controlleren
    int[] leftDir = {-9, -1, 7, -8, 8}; // Alle richtingen behalve de rechterkant
    int[] rightDir = {-7, 1, 9, -8, 8}; // Alle richtingen behalve de linkerkant
    private AIController ai;

    /**
     * Constructor voor de ReversiController. Deze gebruikt ook de SimpleGameController
     *
     * @param model Model van de game die wordt gebruikt
     * @param primaryStage De Stage voor javaFX waarin de views worden geladen.
     * @param gameView De view die het speel weergeeft.
     * @param info Een HashMap met info over het spel dat van de server is ontvangen.
     */
    public ReversiController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        // Zet het veld op
        if (!gameModel.isYourTurn()) {
            gameModel.setPlayFieldAtIndex(27, 1);
            gameModel.setPlayFieldAtIndex(28, 2);
            gameModel.setPlayFieldAtIndex(35, 2);
            gameModel.setPlayFieldAtIndex(36, 1);
        } else {
            gameModel.setPlayFieldAtIndex(27, 2);
            gameModel.setPlayFieldAtIndex(28, 1);
            gameModel.setPlayFieldAtIndex(35, 1);
            gameModel.setPlayFieldAtIndex(36, 2);
        }

        occupied.add(27);
        occupied.add(28);
        occupied.add(35);
        occupied.add(36);

        primaryStage.setTitle("Reversi!");
        super.updateGame();
        new Thread(new MoveListener()).start();
    }

    /**
     * Haal alle steentjes op die moeten worden omgedraaid zodra je een zet op de meegegeven index doet.
     *
     * @param index De plek waar je een steentje neer legt.
     * @return Een lijst met alle steentjes die moeten worden omgedraaid, exclusief de index zelf.
     */
    public List getMoves(int index) {
        List toChange = new ArrayList();
        for (int i : directions) {
            try {
                if (gameModel.getPlayField()[index + i] == (gameModel.isYourTurn() ? 2 : 1))
                    toChange.addAll(checkDir(i, index));
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        return toChange;
    }

    /**
     * Controleert of dit een legale zet is of niet.
     *
     * @param index index van de zet die is meegegeven. Deze is te vergelijken met het playfield van de
     *              game model.
     * @return boolean die aangeeft of de zet legaal is of niet
     */
    @Override
    public boolean legalMove(int index) {
        return super.legalMove(index) && getMoves(index).isEmpty();
    }

    /**
     * Voeg de onclick-functionaliteiten toe aan de circle van de desbetreffende positie in het speelveld.
     *
     * @param i index van de steen in de model.gamestate- en pieces array
     */
    protected void setOnClick(int i) {
        Circle r = pieces[i];
        if (!occupied.contains(i)) {
            r.setFill(Color.YELLOW);
        }

        r.setOnMouseClicked(e -> {
            try {
                ClientCommands.sendMove(i);
                updateBoard(i);
                gameModel.setYourTurn(false);
                gameView.setTurn(gameModel.getOpponent());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            r.setDisable(true);
        });
    }

    /**
     * Update het speelveld en de game en voeg de nieuwe zet toe aan de occupied array
     *
     * @param i Index van de zet in het speelveld
     */
    public void updateBoard(int i) {
        gameModel.updatePlayField(getMoves(i));
        super.updateBoard(i);
        occupied.add(i);
        super.updateGame();
    }

    /**
     * Geeft een set terug met alle mogelijke zetten van de huidige spelsituatie.
     *
     * @return Een set met alle mogelijke zetten en gevolgen. Hierbij is de positie waarop je de steen zet
     *         de key en een set van alle andere posities die veranderen de value.
     */
    public Set getPossibleList() {
        Set check = new HashSet();
        for (int item : occupied) {
            if (item % 8 == 0) {
                for (int i : rightDir) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            } else if (item + 1 % 8 == 0) {
                for (int i : leftDir) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            } else {
                for (int i : directions) {
                    try {
                        if (gameModel.getPlayField()[item + i] == 0) check.add(item + i);
                    } catch (ArrayIndexOutOfBoundsException e) {}
                }
            }
        }
        return check;
    }

    /**
     * Controleert een richting vanaf een beginpunt om te controleren welke posities er allemaal veranderen.
     *
     * @param dir Richting waar je op controleert
     * @param current huidige index vanaf waar je wilt controleren, Exclusief eerste zet.
     * @return Een lijst met alle posities die veranderen van een richting zodra je de meegegeven zet doet
     */
    private List checkDir(int dir, int current) {
        try {
            ArrayList series = new ArrayList();
            current += dir;
            int currentValue = gameModel.getPlayField()[current];
            while (currentValue == (gameModel.isYourTurn() ? 2 : 1)) {
                // Controleer tot aan de randen
                if (current % 8 == 0 || (current + 1) % 8 == 0) {
                    // Ga alleen nog door als je verticaal aan het controleren bent
                    if (dir == 8 || dir == -8) {
                        series.add(current);
                        current += dir;
                        currentValue = gameModel.getPlayField()[current];
                    } else
                        break;
                } else {
                    series.add(current);
                    current += dir;
                    currentValue = gameModel.getPlayField()[current];
                }
            }
            if (gameModel.getPlayField()[current] == (gameModel.isYourTurn() ? 1 : 2)) return series;
        } catch (ArrayIndexOutOfBoundsException e) {}
        return new ArrayList();
    }

    /**
     * Update de game, in het geval van een AI roep je deze ook aan. Als dit niet het geval is, worden alle
     * mogelijke zetten berekend en krijgen deze een onclick toegewezen die de beurt doorsturen.
     */
    public void updateGame() {
        Platform.runLater(() -> {
            if (Settings.getInstance().getAI()) {
                // Wacht tot de AI geladen is om NullPointer te voorkomen
                while (ai == null) {}
                gameView.setTurn(gameModel.getOpponent());
                ai.doTurn();
                gameModel.setYourTurn(false);
            } else {
                ArrayList<Integer> possMoves = new ArrayList<>();
                possMoves.addAll(getPossibleList());
                for (int i = 0; i < possMoves.size(); i++) {
                    List toChange = getMoves(possMoves.get(i));
                    if (!toChange.isEmpty()) {
                        setOnClick(possMoves.get(i));
                    }
                }
            }
        });
    }

    public void setAi(AIController ai) {
        this.ai = ai;
    }

    public List getOccupied() {
        return occupied;
    }
}
