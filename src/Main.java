import controllers.GameSelectController;
import controllers.simpleGame.SimpleGameController;
import controllers.simpleGame.TicTacToeController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Game;
import views.GameView;
import views.TicTacToeView;

public class Main extends Application {
    public void start(Stage primaryStage) {
        /*try {
            Client.getInstance().startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        TicTacToeController controller = new TicTacToeController(new SimpleGameController(new Game(3, 3), primaryStage, (GameView) new TicTacToeView()));
        //primaryStage.setScene(new Scene(new LoginView()));
        primaryStage.show();
    }
    
    public static void main(String[] args){
	launch(args);
    }
}