import controllers.GameSelectController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) {
        //Game ttt = new Game(3, 3);
        //GameController controller = new GameController(ttt);
        //Scene scene = (Scene) controller.getGameView();

        GameSelectController controller = new GameSelectController(primaryStage);
        primaryStage.show(); // Display the stage
    }
}