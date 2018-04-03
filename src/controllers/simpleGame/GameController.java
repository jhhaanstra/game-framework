package controllers.simpleGame;

public interface GameController {
    public boolean legalMove();
    public void updateGame(int index, int value);
    public void updateView();
}
