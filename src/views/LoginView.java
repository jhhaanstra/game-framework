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

public class LoginView extends Scene {
    private GridPane pane;
    private TextField textField;
    private Button loginButton;

    public LoginView(){
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setPrefSize(400,450);
        pane.setAlignment(Pos.TOP_CENTER);

        Label text1 = new Label("Project 2.3 Applicatie 2.0");
        Label text2 = new Label("Enter a user name : ");
        pane.add(text1, 0,0);
        pane.add(text2, 0,2);

        pane.setMargin(text1, new Insets(0,0,45,0));
        textField = new TextField();
        pane.setMargin(textField, new Insets(15,0,15,0));
        pane.add(textField, 0, 3);
        loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(text1, HPos.CENTER);
        GridPane.setValignment(text1, VPos.TOP);
        GridPane.setHalignment(text2, HPos.CENTER);
        pane.add(loginButton, 0, 4);


        Image image = new Image(getClass().getResourceAsStream("../img/logo.png"));
        Label label1 = new Label("s");
        label1.setGraphic(new ImageView(image));

        pane.add(label1, 0,1);
        pane.setMargin(label1, new Insets(0,0,25,0));
    }

    public GridPane getPane() {
        return pane;
    }

    public Button getLoginButton(){
        return loginButton;
    }

    public TextField getTextField() { return textField; }
} // end of login view




