package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ModeView extends Scene {
    private GridPane pane;
    private Button AIButton;
    private Button playerButton;
    private Button backButton;

    public ModeView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setPrefSize(400,450);
        pane.setAlignment(Pos.TOP_CENTER);

        Label text1 = new Label("Choose AI or player");
        pane.setMargin(text1, new Insets(0,0,45,0));
//        Label text2 = new Label("AI");
 //       Label text3 = new Label("Player");
        pane.add(text1, 0,0);
  //      pane.add(text2, 0,2);
    //    pane.add(text3, 0,3);

        AIButton = new Button("AI");
        pane.add(AIButton, 0, 1);
        GridPane.setHalignment(AIButton, HPos.CENTER);
        pane.setMargin(AIButton, new Insets(0,0,45,0));

        AIButton.setPrefWidth(100);

        playerButton = new Button("Player");
        pane.add(playerButton, 0, 2);
        GridPane.setHalignment(playerButton, HPos.CENTER);
        pane.setMargin(playerButton, new Insets(0,0,45,0));
        playerButton.setPrefWidth(100);
        
//        backButton = new Button("Back");
//        pane.add(backButton, 0, 3);
//        GridPane.setHalignment(backButton, HPos.RIGHT);
//        backButton.setPrefWidth(100);
        this.pane = pane;
    }

    public GridPane getPane() {
        return pane;
    }

    public Button getAIButton () {
        return AIButton;
    }
    
    public Button getBackButton () {
        return backButton;
    }

    public Button getPlayerButton() {
        return playerButton;
    }
}
