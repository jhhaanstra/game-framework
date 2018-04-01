import controllers.GameController;
import controllers.GameSelectController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Game;

public class Main extends Application {
    public void start(Stage primaryStage) {
        //Game ttt = new Game(3, 3);
        //GameController controller = new GameController(ttt);
        //Scene scene = (Scene) controller.getGameView();

        GameSelectController controller = new GameSelectController();
        Scene scene = controller.getView();
        primaryStage.setTitle("Choose a game!"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}