package ChatServer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private ArrayList<Rectangle> list = new ArrayList<>();
    private int turn2 = 0;

    private NetworkConnection connection = createClient();


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

    private Client createClient() {
        return new Client("127.0.0.1", 7789, data -> {
            Platform.runLater(() -> {
                messages.appendText("Server: " + data + "\n");
                if (data.contains("SVR GAME MATCH")) {
                    getTicTacToe();
                }
                if (data.contains("SVR GAME MOVE")){
                    HashMap moves = new HashMap();
                    String[] parts = data.substring(15).split(" ");

                    for (int x = 0; x < parts.length - 1; x++) {
                        String value = parts[x + 1].replace("\"", "").replace(",", " ");
                        moves.put(parts[x].replaceAll("[^A-Za-z]+", ""), value);
                        x++;
                    }

                    int count = 0;
                    // Getting the keys
                    for (Object key : moves.keySet() ) {
                        String keyString = moves.get(key).toString();
                        if (count == 1) {
                            String newString = keyString.trim();
                            int value = Integer.parseInt(newString);
                            list.get(value).setFill((turn2 % 2 == 0) ? BLUE : Color.RED);
                        }
                        count++;
                    }
                    turn2 += 1;

                }
            });
        });
    }

    private void getTicTacToe() {
        new TicTacToe().start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }

    class TicTacToe extends Application {
        private int turn = 0;

        public void start(Stage primaryStage) {
            GridPane pane = new GridPane();
            int position = 0;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    Rectangle r = new Rectangle(100, 100);
                    r.setFill(javafx.scene.paint.Color.WHITE);
                    r.setStroke(javafx.scene.paint.Color.BLACK);
                    r.setX(position);

                    r.setOnMouseClicked(e -> {
                        r.setFill((turn % 2 == 0) ? BLUE : Color.RED);
                        incrementTurn();
                        try {
                            String value  = Double.toString(r.getX());
                            connection.send("move " + value.substring(0, value.indexOf(".")));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        r.setDisable(true);
                    });
                    pane.add(r, x, y);
                    position++;
                    list.add(r);
                }
            }


            Scene scene = new Scene(pane);
            primaryStage.setTitle("Tic Tac Toe"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
        }

        public void incrementTurn() {
            turn++;
        }


    }
}