package controllers;

import controllers.simpleGame.ReversiController;
import models.ClientCommands;
import models.Game;

import java.util.*;

/**
 * Deze class zorgt voor de innerlijke werkingen van de AI
 * hoe de AI haar zetten kiest samen met de methodes die ervoor gebruikt worden
 */
public class AIController extends Thread{
    private ReversiController gameController;
    private ArrayList<Integer> choices;
    private ArrayList<Integer> innerST;
    private ArrayList<Integer> innerPoss;
    private HashMap<Integer, List> save;

    public AIController(ReversiController gameController) {
        this.gameController = gameController;
        choices = new ArrayList<>();
        innerST = new ArrayList<>();
        innerPoss = new ArrayList<>();
        innerST.addAll(Arrays.asList(19, 20, 26, 29, 34, 37, 43, 44));
        save = new HashMap<>();
        start();
    }

    /**
     * de doTurn method word gebruikt om de positie van de zet
     * die de AI gaat zetten te bepalen 
     */
    public void doTurn() {
        HashMap<Integer, List> save = new HashMap<>();

        Set<Integer> possMoves = gameController.getPossibleList();
        for (int i : possMoves) {
            List toChange = gameController.getMoves(i);
            if (!toChange.isEmpty()) {
                save.put(i, toChange);
                choices.add(i);
            }
        }

	for(int option : innerST){
	    if(Game.getPlayFieldAtIndex(option) == 2 && checkLineContains(option) != 0)
		    innerPoss.add(checkLineContains(option));
	}

        if (!choices.isEmpty()) {
            checkCorner(gameController.getOccupied(), 0, new int[] {1, 8, 9});
            checkCorner(gameController.getOccupied(), 7, new int[] {6, 14, 15});
            checkCorner(gameController.getOccupied(), 56, new int[] {48, 49, 57});
            checkCorner(gameController.getOccupied(), 63, new int[] {54, 55, 62});
            if (choices.contains(0) || 
                    choices.contains(7) || 
                        choices.contains(56) || 
                            choices.contains(63)) {
                for(int corner : new int[] {0, 7, 56, 63}){
                    if(choices.contains(corner)){
                        ClientCommands.sendMove(corner);
                        gameController.updateBoard(corner);
                        emptyLists();
                    }
                }           
            } else if(Game.getPlayFieldAtIndex(27) == 2 && checkLineContains(27) != 0 ||
                        Game.getPlayFieldAtIndex(28) == 2 && checkLineContains(28) != 0 ||
                        Game.getPlayFieldAtIndex(35) == 2 && checkLineContains(35) != 0 ||
                        Game.getPlayFieldAtIndex(36) == 2 && checkLineContains(36) != 0 ) {
                for(int middle : new int[] {27, 28, 35, 36}) {
                    if(Game.getPlayFieldAtIndex(middle) == 2 && checkLineContains(middle) != 0){
                        ClientCommands.sendMove(checkLineContains(middle));
                        gameController.updateBoard(checkLineContains(middle));
                        emptyLists();
                    }
                }
            } else if (gameController.getOccupied().size() < 32) {
                for (int y = 2; y < 6; y++) {
                    for (int x = 2; x < 6; x++) {
                        if (Game.getPlayFieldAtIndex((y * 8) + x) == 2 && checkLineContains((y * 8) + x) != 0) {
                            ClientCommands.sendMove(checkLineContains((y * 8) + x));
                            gameController.updateBoard(checkLineContains((y * 8) + x));
                            emptyLists();
                        }
                    }
                }
            } else {
                Random rand = new Random();
                int x = rand.nextInt(choices.size());
                ClientCommands.sendMove(choices.get(x));
                gameController.updateBoard(choices.get(x));
                emptyLists();
            }
        }
    }

    /**
     * kijkt of je een steentje kunt krijgen bij een bepaalde keuze
     * @param index
     * @return de keuze waarmee je index kunt krijgen
     */
    private int checkLineContains(int index){
        for(int choice : choices){
            if(gameController.getMoves(choice).contains(index)) return choice;
        }
        return 0;
    }
    
    /**
     * maakt alle lijsten leeg
     */
    private void emptyLists() {
        choices.clear();
        save.clear();
        innerPoss.clear();
    }

    /**
     * zorgt ervoor dat de AI zo min mogelijk de posities om de hoeken pakt
     * @param occupied
     * @param startPoss
     * @param items 
     */
    private void checkCorner(List occupied, int startPoss, int[] items) {
        if (!occupied.contains(startPoss)) {
            for (int index = 0; index < items.length; index++) {
                if (choices.contains(items[index]) && choices.size() > 1) {
                    choices.remove(choices.indexOf(items[index]));
                }
            }
        }
    }
}