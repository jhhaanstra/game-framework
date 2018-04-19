package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
/**
 * deze class zorgt voor de layout en het uiterlijk voor het selecteren van
 * een speler of een AI
 */
public class ModeView extends Scene {
    private GridPane pane;
    private Button AIButton;
    private Button playerButton;
    private Button backButton;

    /**
     * zorgt ervoor dat alles op de goede plek in de gridpane wordt gezet 
     */
    public ModeView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setPrefSize(400,450);
        pane.setAlignment(Pos.TOP_CENTER);

        Label text1 = new Label("Choose AI or player");
        pane.setMargin(text1, new Insets(0,0,45,0));
        pane.add(text1, 0,0);

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

        this.pane = pane;
    }

    /**
     * geeft de status van de gridpan
     * @return grid pane
     */
    public GridPane getPane() {
        return pane;
    }

    /**
     * geeft de status van de AI knop
     * @return AI knop
     */
    public Button getAIButton () {
        return AIButton;
    }
    
    /**
     * geeft de status van de terug knop
     * @return terug knop
     */
    public Button getBackButton () {
        return backButton;
    }

    /**
     * geeft de status van de speler knop
     * @return speler knop
     */
    public Button getPlayerButton() {
        return playerButton;
    }
}
