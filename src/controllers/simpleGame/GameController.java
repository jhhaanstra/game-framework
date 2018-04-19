package controllers.simpleGame;

public interface GameController {
    public boolean legalMove(int index);
    public void updateGame();
    public void updateBoard(int i);
}
