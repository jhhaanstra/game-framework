package controllers.simpleGame;

public class GameDecoratorController implements GameController {
    protected final GameController decoratedGameController;

    public GameDecoratorController(GameController decoratedGameController) {
        this.decoratedGameController = decoratedGameController;
    }

    @Override
    public boolean legalMove() {
        return decoratedGameController.legalMove();
    }

    @Override
    public void updateGame(int index, int value) {
        decoratedGameController.updateGame(index, value);
    }

    @Override
    public void updateView() {
        decoratedGameController.updateView();
    }
}
