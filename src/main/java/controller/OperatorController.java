package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Operator;
import service.OperatorService;
import utils.ListEvent;
import utils.Observer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OperatorController implements Observer<Operator> {
    private ObservableList<Operator> model = FXCollections.observableArrayList();
    private OperatorService service;

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;

    public OperatorController() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("db.config"));
        } catch (IOException ex) {
            System.out.println("cannot find db.config " + ex);
        }
        this.service = new OperatorService(prop);
    }

    @Override
    public void notifyEvent(ListEvent<Operator> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(), false).collect(Collectors.toList()));
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        if (service.login(Integer.parseInt(user.getText()), pass.getText())) {
            Stage parent = (Stage) user.getScene().getWindow();
            parent.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainFXML.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Main");
                stage.setScene(new Scene(root, 650, 480));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
