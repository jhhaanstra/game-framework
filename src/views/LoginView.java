package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView extends Scene {
    private GridPane pane;
    private TextField textField;
    private Button loginButton;

    public LoginView(){
        super(new GridPane());
        pane = (GridPane) super.getRoot();
        pane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        pane.setVgap(5.5);
        pane.setAlignment(Pos.CENTER);
        pane.add(new Label("User Name: "), 0 , 0);
        textField = new TextField();
        pane.add(textField, 1, 0);
        loginButton = new Button("Login");
        pane.add(loginButton, 0, 1);
    }

    public GridPane getPane() {
        return pane;
    }

    public Button getLoginButton(){
        return loginButton;
    }

    public TextField getTextField() { return textField; }
}