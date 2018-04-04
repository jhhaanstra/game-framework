package controllers.simpleGame;

public class TicTacToeController extends GameDecoratorController {

    public TicTacToeController(SimpleGameController simpleGame) {
        super(simpleGame);

    }

    @Override
    public boolean legalMove() {
        if (super.legalMove())
            return true;
        return false;
    }

    @Override
    public void updateGame(int index, int value) {
        super.updateGame(index, value);
    }

    @Override
    public void updateView() {
        super.updateView();
    }
}
