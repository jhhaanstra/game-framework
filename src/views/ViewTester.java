package views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewTester extends Application {
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setVgap(5.5);
        pane.setAlignment(Pos.CENTER);
        pane.add(new Label("Choose your game"), 0, 0);
        Button tttButton = new Button("TicTacToe");
        pane.add(tttButton, 0, 1);
        pane.add(new Button("Reversi"), 0, 2);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Choose a game"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}
