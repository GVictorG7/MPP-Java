package service;

import domain.Cursa;
import domain.Operator;
import domain.Participant;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IService extends Remote, Serializable {
    Operator login(int id, String password) throws MyException, RemoteException;

    Boolean usersExist(int id) throws RemoteException;

    String getPass(int id) throws RemoteException;

    List<Cursa> getAllCurse() throws RemoteException;

    List<Participant> getAllParticipanti() throws RemoteException;

    // List<Echipe> getAllEchipe() throws RemoteException;

    void saveParticipant(int id, String nume, String echipa, int cap, int idCursa) throws RemoteException;

    List<Participant> getByEchipe(String nume) throws RemoteException;

    void addObserever(IObserver observer) throws RemoteException;
}
