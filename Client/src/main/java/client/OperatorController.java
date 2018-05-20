package client;

import domain.Operator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.IService;
import service.MyException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class OperatorController implements IController, Remote {
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    private StageManager stageManager;
    private IService service;

    public OperatorController() {
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        try {
            login();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainFXML.fxml"));
            Parent rootNode = loader.load();
            stageManager.switchScene(FXMLEnum.MAIN, rootNode, loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (service.login(Integer.parseInt(user.getText()), pass.getText())) {
//            Stage parent = (Stage) user.getScene().getWindow();
//            parent.hide();
//            try {
//                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainFXML.fxml"));
//                Stage stage = new Stage();
//                stage.setTitle("Main");
//                stage.setScene(new Scene(root, 650, 480));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private Operator login() {
        try {
            System.out.println(user.getText() + " " + pass.getText());
            return service.login(Integer.parseInt(user.getText()), pass.getText());
        } catch (MyException | RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void initialize() {
//        assert this.user != null : "fx:id=\"user\" was not injected: check your FXML file 'LoginPage.fxml'.";
//        assert this.pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'LoginPage.fxml'.";
    }

    @Override
    public void initialize(StageManager stageManager, IService service) {
        this.stageManager = stageManager;
        this.service = service;
    }
}
