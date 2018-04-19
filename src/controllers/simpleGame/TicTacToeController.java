package controllers.simpleGame;

import javafx.stage.Stage;
import models.*;
import views.GameView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Deze klasse definieert alle regels die Tic-Tac-Toe nodig heeft, boven op de SimpleGameController regels.
 */
public class TicTacToeController extends SimpleGameController implements GameController {
    /**
     * Constructor voor de Tic-Tac-ToeController. Deze gebruikt ook de SimpleGameController
     * @param model Model van de game die wordt gebruikt
     * @param primaryStage De Stage voor javaFX waarin de views worden geladen.
     * @param gameView De view die het speel weergeeft.
     * @param info Een HashMap met info over het spel dat van de server is ontvangen.
     */
    public TicTacToeController(Game model, Stage primaryStage, GameView gameView, HashMap info) {
        super(model, primaryStage, gameView, info);
        primaryStage.setTitle("Tic-Tac-Toe!");
        super.updateGame();
        new Thread(new MoveListener()).start();
}

    /**
     * Controleer of de meegegeven zet een legale zet is
     * @param index index van de zet die is meegegeven. Deze is te vergelijken met het playfield van de
     *              game model.
     * @return Boolean die aangeeft of de move legaal is of niet.
     */
    @Override
    public boolean legalMove(int index) {
        return super.legalMove(index);
    }

    /**
     * Update de game, in het geval van een AI. Laat de AI dan een willekeurige zet doen.
     */
    @Override
    public void updateGame() {
        super.updateGame();

        if (Settings.getInstance().getAI()) {
            ArrayList<Integer> choices = new ArrayList<>();
            for (int i = 0; i < pieces.length; i++) {
                if (legalMove(i)) choices.add(i);
            }
            if (!choices.isEmpty()) {
                int x = new Random().nextInt(choices.size());
                ClientCommands.sendMove(choices.get(x));
                updateBoard(choices.get(x));
                gameModel.updatePlayField(choices.get(x));
                gameModel.setYourTurn(false);
                updateGame();
            }
        } else {
            for (int i = 0; i < pieces.length; i++) {
                if (legalMove(i)) setOnClick(i);
            }
        }
        primaryStage.setScene(this.gameView);
    }

    /**
     * Update het speelveld en tel de beurt op.
     * @param i Index van de zet in het speelveld
     */
    public void updateBoard(int i) {
        gameModel.updatePlayField(i);
        gameModel.incrementTurn();
    }
}
