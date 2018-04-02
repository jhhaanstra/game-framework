package controllers.simpleGame;

import controllers.simpleGame.GameDecoratorController;
import controllers.simpleGame.SimpleGameController;

public class TicTacToeController extends GameDecoratorController {
    public TicTacToeController(SimpleGameController simpleGame) {
        super(simpleGame);
    }

    @Override
    public boolean legalMove() {
        return true;
    }

    @Override
    public void updateGame() {
        // pass
    }

    @Override
    public void updateView() {
        // pass
    }
}
