package client;

import service.IObserver;
import service.IService;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class MainControllerSerializable implements IObserver, Serializable, Remote, IController {
    private IService srv;
    private StageManager stageManager;

    public MainControllerSerializable(IService service) {
        this.srv = service;
    }

//    public void handleLogOut(ActionEvent event) throws IOException {
//    }

    public void handleSaveParticipant(int id, String nume, String echipa, int cap, int idC) throws RemoteException {
        srv.saveParticipant(id, nume, echipa, cap, idC);
    }

//    public void initialize(StageManager stageManager, IService service) {
//    }

    @Override
    public void update() {
    }

    @Override
    public void initialize(StageManager stageManager, IService iService) {
        this.srv = iService;
        this.stageManager = stageManager;
    }
}
