import controllers.GameSelectController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) {
        GameSelectController controller = new GameSelectController(primaryStage);
        primaryStage.show();
    }
}