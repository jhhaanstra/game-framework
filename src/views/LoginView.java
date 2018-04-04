package views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.Client;

public class LoginView extends GridPane {
    public LoginView() {
        super();
        TextField textField = new TextField();
        add(textField, 0, 0);
        Button btn = new Button("Log in");
        btn.setOnAction(e -> {
            if (Client.getInstance().login(textField.getText()))
                System.out.println("you logged in!");
        });
        add(btn, 0, 1);

    }
}
