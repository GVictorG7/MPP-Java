package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import service.IService;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartClient extends Application {
    private StageManager stageManager = null;

    private void displayInitialScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OperatorFXML.fxml"));

        Parent rootNode = loader.load();
        stageManager.switchScene(FXMLEnum.LOGIN, rootNode, loader.getController());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6666);
            IService stub = (IService) registry.lookup("IService");
            stageManager = new StageManager(primaryStage, stub);

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
        displayInitialScene();
    }
}
