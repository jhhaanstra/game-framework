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
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class GameLobbyView extends Scene {
    private GridPane pane;
    private Button startButton;
    private Button challengeButton;
    private Button refreshButton;
    private ListView<String> playerList = new ListView<>();
    ObservableList<String> lobbyData = FXCollections.observableArrayList();
    private String challengePlayer;

    public GameLobbyView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setVgap(5.5);
        pane.setAlignment(Pos.CENTER);
        pane.add(new Label("Online players"), 0, 0);

        startButton = new Button("Start Game");
        pane.add(startButton, 0 , 2);

        refreshButton = new Button("Refresh lobby");
        pane.add(refreshButton, 1, 2);

        challengeButton = new Button("Challenge");
        pane.add(challengeButton, 0 , 3);

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


    public GridPane getPane() {
        return pane;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getChallengeButton() { return challengeButton; }

    public ListView getListView() { return playerList; }

    public ObservableList getList() { return lobbyData; }

    public String getPlayer() { return challengePlayer; }

    public Button getRefreshButton() {
        return refreshButton;
    }
}

