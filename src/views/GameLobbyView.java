package views;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * Deze class zorgt voor het uiterlijk en de layout van de game lobby.
 */
public class GameLobbyView extends Scene {
    private GridPane pane;
    private Button challengeButton;
    private Button refreshButton;
    private Button backButton;
    private ListView<String> playerList = new ListView<>();
    ObservableList<String> lobbyData = FXCollections.observableArrayList();
    private String challengePlayer;

    /**
     * zorgt ervoor dat alles op de goede plek in de gridpane wordt gezet 
     */
    public GameLobbyView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setPrefSize(400,450);
        pane.setAlignment(Pos.TOP_CENTER);

        pane.add(new Label("Online players"), 0, 0);

        refreshButton = new Button("Refresh lobby");
        pane.add(refreshButton, 1, 2);

        challengeButton = new Button("Challenge");
        pane.add(challengeButton, 0 , 2);
        
        backButton = new Button("Back");
        pane.add(backButton, 1, 3);

        ListView<String> listView = new ListView<>(lobbyData);
        listView.setPrefSize(200, 250);

        lobbyData.addAll();
        listView.setItems(lobbyData);
        listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val,
            String new_val) -> {
            challengePlayer = new_val;
        });

        pane.add(listView, 0 , 1);
        this.pane = pane;
    }

    /**
     * geeft de status Gridpane terug
     * @return het Gridpane
     */
    public GridPane getPane() { return pane; }

    /**
     * geeft de status van de challenge knop
     * @return de challenge knop
     */
    public Button getChallengeButton() { return challengeButton; }
    
    /**
     * geeft de status van de terug knop
     * @return de terug knop
     */
    public Button getBackButton() { return backButton; }

    /**
     * geeft de status van de spelers lijst
     * @return de spelers lijst
     */
    public ListView getListView() { return playerList; }

    /**
     * geeft de status van lobby
     * @return lobby
     */
    public ObservableList getList() { return lobbyData; }

    /**
     * geeft de uitgedaagde speler
     * @return uitgedaagde speler
     */
    public String getPlayer() { return challengePlayer; }

    /**
     * geeft de status van de refresh knop
     * @return refresh knop
     */
    public Button getRefreshButton() { return refreshButton; }
}

