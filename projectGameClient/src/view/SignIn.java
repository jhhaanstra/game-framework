package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
/**
 *
 * @author Robbert
 */
public class SignIn {
    private Stage myStage;
    
    public SignIn(){
        showStage();
    }
    
    public void setStage(Stage myStage){
        this.myStage = myStage;
    }
    
    private void showStage(){
        myStage.setTitle("reversIT2");
        myStage.show();
    }
}
