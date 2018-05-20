package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cursa;
import model.Participant;
import service.CursaService;
import service.ParticipantiService;
import utils.ListEvent;
import utils.Observer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController implements Observer<Participant> {
    @FXML
    TableView tableViewCurse;
    @FXML
    TableView tableViewEchipa;
    @FXML
    TableColumn tableColumnIdCursa;
    @FXML
    TableColumn tableColumnCapacitate;
    @FXML
    TableColumn tableColumnNr;
    @FXML
    TableColumn tableColumnNume;
    @FXML
    TableColumn tableColumnCapEchipe;
    @FXML
    ComboBox<Integer> comboBox;
    private ObservableList<Participant> participants = FXCollections.observableArrayList();
    private ObservableList<Cursa> curse;
    private CursaService cursaService;
    private ParticipantiService participantiService;
    @FXML
    private TextField searchEchipa;
    @FXML
    private TextField ParticipantNume;
    @FXML
    private TextField ParticipantEchipa;

    public MainController() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("db.config"));
        } catch (IOException ex) {
            System.out.println("cannot find db.config");
        }
        this.cursaService = new CursaService(prop);
        this.participantiService = new ParticipantiService(prop);

        curse = FXCollections.observableArrayList(cursaService.getCurse());
    }

    @FXML
    public void initialize() {
        tableColumnIdCursa.setCellValueFactory(new PropertyValueFactory<String, String>("id"));
        tableColumnCapacitate.setCellValueFactory(new PropertyValueFactory<String, String>("cap"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<String, String>("nume"));
        tableColumnCapEchipe.setCellValueFactory(new PropertyValueFactory<String, String>("cap"));
        tableViewCurse.setItems(curse);
        tableViewEchipa.setItems(participants);

        comboBox.getItems().addAll(cursaService.getCap());
    }

    @Override
    public void notifyEvent(ListEvent<Participant> e) {
        participants.setAll(StreamSupport.stream(e.getList().spliterator(), false).collect(Collectors.toList()));
    }

    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        participants = FXCollections.observableArrayList(participantiService.getByEchipa(searchEchipa.getText()));
        tableViewEchipa.setItems(participants);
    }

    @FXML
    public void handleRegistration(ActionEvent actionEvent) {
        participantiService.save(new Participant(participantiService.size() + 1, ParticipantNume.getText(), ParticipantEchipa.getText(), comboBox.getValue(), 1));
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        Stage parent = (Stage) searchEchipa.getScene().getWindow();
        parent.hide();
    }
}
