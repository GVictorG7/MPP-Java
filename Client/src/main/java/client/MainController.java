package client;

import domain.Cursa;
import domain.Participant;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.IObserver;
import service.IService;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class MainController implements IObserver, IController, Remote, Serializable {
    private IService srv;
    private StageManager stageManager;

    @FXML
    private TableView<Cursa> tableViewCurse;
    @FXML
    private TableColumn tableColumnIdCursa;
    @FXML
    private TableColumn tableColumnCapacitate;
    @FXML
    private TableColumn tableColumnNr;
    @FXML
    private TableView<Participant> tableViewEchipa;
    @FXML
    private TableColumn tableColumnNume;
    @FXML
    private TableColumn tableColumnCapEchipe;
    @FXML
    private TextField searchEchipa;
    @FXML
    private TextField ParticipantId;
    @FXML
    private TextField ParticipantNume;
    @FXML
    private TextField ParticipantEchipa;

    @FXML
    public void handleLogOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXMLEnum.LOGIN.getFxmlFile()));
        Parent rootNode = loader.load();
        stageManager.switchScene(FXMLEnum.LOGIN, rootNode, loader.getController());
    }

    @FXML
    public void handleSaveParticipant(ActionEvent event) {
        try {
            int capacitate = tableViewCurse.getSelectionModel().getSelectedItem().getCap();
            int idCursa = tableViewCurse.getSelectionModel().getSelectedItem().getId();

            srv.saveParticipant(Integer.parseInt(ParticipantId.getText()), ParticipantNume.getText(), ParticipantEchipa.getText(), capacitate, idCursa);
            tableViewCurse.setItems(FXCollections.observableArrayList(srv.getAllCurse()));
            tableViewEchipa.setItems(FXCollections.observableArrayList(srv.getAllParticipanti()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCauta(ActionEvent event) {
        try {
            String nume = searchEchipa.getText();
            tableViewEchipa.setItems(FXCollections.observableArrayList(srv.getByEchipe(nume)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(StageManager stageManager, IService service) {
        this.stageManager = stageManager;
        this.srv = service;
        try {
            srv.addObserever(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        initTable();
    }

    private void initTable() {
        try {
            tableColumnNume.setCellValueFactory(new PropertyValueFactory<Participant, String>("nume"));
            tableColumnCapEchipe.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("cap"));
            tableColumnIdCursa.setCellValueFactory(new PropertyValueFactory<Cursa, Integer>("id"));
            tableColumnCapacitate.setCellValueFactory(new PropertyValueFactory<Cursa, Integer>("cap"));
            tableColumnNr.setCellValueFactory(new PropertyValueFactory<Cursa, Integer>("nrPart"));
            tableViewCurse.setItems(FXCollections.observableArrayList(srv.getAllCurse()));
            tableViewEchipa.setItems(FXCollections.observableArrayList(srv.getAllParticipanti()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            try {
                tableViewCurse.setItems(FXCollections.observableArrayList(srv.getAllCurse()));
                tableViewEchipa.setItems(FXCollections.observableArrayList(srv.getAllParticipanti()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
