import controllers.GameSelectController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Client;
import views.LoginView;

public class Main extends Application {
    public void start(Stage primaryStage) {
        try {
            Client.getInstance().startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //GameSelectController controller = new GameSelectController(primaryStage);
        primaryStage.setScene(new Scene(new LoginView()));
        primaryStage.show();
    }
    
    public static void main(String[] args){
	launch(args);
    }
}