package client;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.IService;

public class StageManager {
    private final Stage primaryStage;
    private final IService service;

    public StageManager(Stage primaryStage, IService service) {
        this.primaryStage = primaryStage;
        this.service = service;
    }

    public synchronized void switchScene(final FXMLEnum view, Parent rootNode, IController controller) {
        controller.initialize(this, service);
        show(rootNode, view.getTitle());
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage() + " ");
            exception.printStackTrace();
        }
    }

    private Scene prepareScene(Parent rootnode) {
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }
}
