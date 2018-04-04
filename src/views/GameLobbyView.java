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
    private TextArea textArea;
    private Button startButton;
    private ListView<String> playerList = new ListView<>();
    ObservableList<String> data = FXCollections.observableArrayList();

    public GameLobbyView() {
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setVgap(5.5);
        pane.setAlignment(Pos.CENTER);
        pane.add(new Label("Online players"), 0, 0);
        /*textArea = new TextArea();
        textArea.setStyle("-fx-control-inner-background:#f2f2f2; -fx-font-family: Tahoma;");
        pane.add(textArea, 0, 1);*/
        startButton = new Button("Start Game");
        pane.add(startButton, 0 , 2);

        ListView<String> listView = new ListView<String>(data);
        listView.setPrefSize(200, 250);

        data.addAll();
        listView.setItems(data);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    System.out.println("Challenge " + new_val);
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

    public TextArea getTextArea() { return textArea; }

    public ListView getListView() { return playerList; }

    public ObservableList getList() { return data; }

}