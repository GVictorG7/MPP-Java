import controller.OperatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OperatorFXML.fxml"));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        OperatorController controller = loader.getController();

        primaryStage.setScene(new Scene(rootLayout, 600, 400));
        primaryStage.show();
    }
}
