package views;

import controllers.simpleGame.SimpleGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Player;

/**
 * Dit is de hoofd class om de spellen weer te kunnen geven. de verschillende
 * spellen zullen deze class extenden. Tevens als het op de goede plek zetten
 * van de layout.
 */
public class GameView extends Scene {
    private BorderPane pane;
    private String playerColor;
    private String opponentColor;
    private Label opponent = new Label();
    private Label turn = new Label();
    
    /**
    * zorgt ervoor dat alles op de goede plek in de gridpane wordt gezet 
    */
    public GameView() {
        super(new BorderPane());
        pane = (BorderPane) super.getRoot();
        setPlayerColor(SimpleGameController.getStartColor());
        setOpponentColor(SimpleGameController.getOpponentColor());
        GridPane score = new GridPane();

        Label name = new Label("Naam : ");
        Label aandebeurt = new Label("Aan de beurt: ");
        Label tegenstander = new Label("Tegenstander : ");
        Label footertext = new Label("Build by ReverseIT");

        tegenstander.setTextFill(Color.web("#0076a3"));
        footertext.setTextFill(Color.web("#0076a3"));
        tegenstander.setTextFill(Color.web("#0076a3"));
        name.setTextFill(Color.web("#0076a3"));
        aandebeurt.setTextFill(Color.web("#0076a3"));

        score.add(new Label(Player.getInstance().getName()), 1, 0);
        score.add(new Label(playerColor), 2, 0);

        score.add(name, 0,0);
        score.add(tegenstander, 0, 1);
        score.add(aandebeurt,0,2);
        score.add(opponent, 1, 1);


        score.add(new Label(opponentColor), 2, 1);
        score.add(turn, 1, 2);
        score.setAlignment(Pos.TOP_LEFT);
        score.setMargin(name, new Insets(15,0,15,50));
        score.setMargin(tegenstander, new Insets(15,0,15,50));
        score.setMargin(aandebeurt, new Insets(15,0,15,50));

        GridPane footer = new GridPane();
        footer.setAlignment(Pos.CENTER);
        footer.add(footertext,0,0);
        footer.setMargin(footertext, new Insets(15,0,15,0));

        pane.setTop(score);
        pane.setPrefSize(500,600);
        pane.setCenter(new GridPane());
        pane.setBottom((footer));

    }

    /**
     * zorgt dat het speelbord wordt gemaakt en alligned wordt
     * @param grid 
     */
    public void setGrid(GridPane grid) {
        pane.setCenter(grid);
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * geeft de status van het speelbord
     * @return speelbord
     */
    public GridPane getGrid() {
        return (GridPane) pane.getCenter();
    }

    /**
     * zorgt dat naam van de tegenstander wordt gegeven
     * @param name 
     */
    public void setOpponent(String name) {
        opponent.setText(name);
    }

    /**
     * geeft de speler die aan de beurt is
     * @param name 
     */
    public void setTurn(String name) {
        turn.setText(name);
    }   
    
    /**
     * zet de kleur van de speler
     * @param color 
     */
    public void setPlayerColor(Color color){
        if(color == Color.WHITE){
            playerColor = " White";
        } else {
            playerColor = " Black";
        }
    }
    
    /**
     * zet de kleur van de tegenstander
     * @param color 
     */
    public void setOpponentColor(Color color){
        if(color == Color.WHITE) opponentColor = " Black";
        else opponentColor = " White";
    }
}
