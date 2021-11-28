import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    private final double MIN_WINDOW_WIDTH = 800;
    private final double MIN_WINDOW_HEIGHT = 400;
    private final double WINDOW_WIDTH = 1200;
    private final double WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        Controller controller = new Controller(model);
        model.setControllerRef(controller);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/avatorMaker.fxml"));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setMinWidth(MIN_WINDOW_WIDTH);
        stage.setMinHeight(MIN_WINDOW_HEIGHT);
        stage.show();
    }
}