package ChatServer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.paint.Color.BLUE;

public class App extends Application {

    private TextArea messages = new TextArea();
    ScrollPane scrollPane = new ScrollPane();

    private NetworkConnection connection = Client.getInstance();

    CheckOutput check = new CheckOutput();

    private Parent createContent() {
        messages.setEditable(false);
        messages.setStyle("-fx-control-inner-background:#f2f2f2; -fx-font-family: Tahoma;");
        messages.setPrefHeight(550);
        scrollPane.setContent(messages);
        TextField input = new TextField();

        input.setOnAction(event -> {
            String message;
            message = input.getText();
            input.clear();

            messages.appendText("Client: " + message + "\n");

            try {
                connection.send(message);
            } catch (Exception e) {
                e.printStackTrace();
                messages.appendText("Failed to send \n");
            }
        });


        VBox root = new VBox(20, messages, input);
        root.setPrefSize(600, 600);
        return root;
    }

    public void init() throws Exception {
        connection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

/*    private Client createClient() {
        return new Client("127.0.0.1", 7789, data -> {
            Platform.runLater(() -> {
                messages.appendText("Server: " + data + "\n");
                check.directInput(data);
            });
        });
    }*/

    /*private void getTicTacToe() {
        new TicTacToe().start(new Stage());
    }*/

    public static void main(String[] args) {
        launch(args);
    }


}