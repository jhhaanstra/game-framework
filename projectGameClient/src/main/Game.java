package main;

import view.SignIn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class Game extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Stage stage = new Stage();
       // Button btOK = new Button("test");
        //Scene scene = new Scene(btOK, 200, 250);
        primaryStage.setTitle("reversIT");
        //primaryStage.setScene(scene);
        SignIn signIn = new SignIn();
        signIn.setStage(primaryStage);
        primaryStage.show();
    }
    
    
    public Game(){
        System.out.println("Maak hier het eerste scherm van de game client");

    }
    
    public void updateScene(Stage primaryStage){
        Button btOK = new Button("test");
        Scene scene = new Scene(btOK, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
