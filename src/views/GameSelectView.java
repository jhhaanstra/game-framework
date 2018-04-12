package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameSelectView extends Scene {
    private GridPane pane;
    private Button tttButton;
    private Button reversiButton;

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
        pane.add(tttButton, 1, 1);
        GridPane.setHalignment(tttButton, HPos.CENTER);
	    reversiButton = new Button("Reversi");
        reversiButton.setPrefWidth(100);
        tttButton.setPrefWidth(100);
        pane.add(reversiButton, 1, 2);
        GridPane.setHalignment(reversiButton, HPos.CENTER);
        this.pane = pane;




        Image image1 = new Image(getClass().getResourceAsStream("../ttt_icon.png"));
        Image image2 = new Image(getClass().getResourceAsStream("../reversi_icon.png"));
        Label imageLabel1 = new Label("");
        Label imageLabel2 = new Label("");
        imageLabel1.setGraphic(new ImageView(image1));
        imageLabel2.setGraphic(new ImageView(image2));

        pane.add(imageLabel1,0,1);
        pane.add(imageLabel2,0,2);

        pane.setMargin(imageLabel1, new Insets(0,0,45,0));
        pane.setMargin(tttButton, new Insets(0,0,45,0));


    }

    public GridPane getPane() {
        return pane;
    }

    public Button getTttButton() {
        return tttButton;
    }
    
    public Button getReversiButton() {
	return reversiButton;
    }
}
