package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Deze class zorgt voor het uiterlijk en de layout voor het selecteren van 
 * een game
 */
public class GameSelectView extends Scene {
    private GridPane pane;
    private Button tttButton;
    private Button reversiButton;
    private Button backButton;

    /**
     * zorgt ervoor dat alles op de goede plek in de gridpane wordt gezet 
     */
    public GameSelectView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setPrefSize(400,450);
        pane.setAlignment(Pos.TOP_CENTER);

        Label text1 = new Label("Choose your game");
        pane.add(text1, 1,0);
        pane.setMargin(text1, new Insets(0,0,45,0));
        GridPane.setHalignment(text1, HPos.CENTER);
        tttButton = new Button("TicTacToe");
        tttButton.setPrefWidth(100);
        pane.add(tttButton, 1, 1);
        GridPane.setHalignment(tttButton, HPos.CENTER);

        reversiButton = new Button("Reversi");
        reversiButton.setPrefWidth(100);
        pane.add(reversiButton, 1, 2);
        GridPane.setHalignment(reversiButton, HPos.CENTER);
        this.pane = pane;

        backButton = new Button("Back");
        backButton.setPrefWidth(100);
        GridPane.setHalignment(backButton, HPos.CENTER);
        pane.add(backButton, 1, 3);

        Image image1 = new Image(getClass().getResourceAsStream("../img/ttt_icon.png"));
        Image image2 = new Image(getClass().getResourceAsStream("../img/reversi_icon.png"));

        Label imageLabel1 = new Label("");
        Label imageLabel2 = new Label("");
        imageLabel1.setGraphic(new ImageView(image1));
        imageLabel2.setGraphic(new ImageView(image2));

        pane.add(imageLabel1,0,1);
        pane.add(imageLabel2,0,2);

        pane.setMargin(imageLabel1, new Insets(0,0,45,0));
        pane.setMargin(tttButton, new Insets(0,0,45,0));

        pane.setMargin(imageLabel2, new Insets(0,0,45,0));
        pane.setMargin(reversiButton, new Insets(0,0,45,0));
    }


    /**
     * geeft de status Gridpane terug
     * @return het Gridpane
     */
    public GridPane getPane() {
        return pane;
    }

    /**
     * geeft de status van de tic-tac-toe knop
     * @return de TTT knop
     */
    public Button getTttButton() {
        return tttButton;
    }
    
    /**
     * geeft de status van de terug knop
     * @return terug knop
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * geeft de status van de reversi knop
     * @return reversi knop
     */
    public Button getReversiButton() {
        return reversiButton;
    }
}
