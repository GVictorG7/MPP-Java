package rpc;

import domain.Cursa;
import domain.Operator;
import domain.Participant;
import service.IObserver;
import service.IService;
import service.MyException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientRPCProxy implements IService {
    private final String host;
    private final int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ClientRPCProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.ADD_PARTICIPANT_TO_A_CURSA) {
            System.out.println("Inintea de creare");
            Thread t1 = new Thread(() -> {
                System.out.println("dupa creare");
                client.update();
            });
            t1.start();
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.ADD_PARTICIPANT_TO_A_CURSA;
    }

    private void sendRequest(Request request) throws MyException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }
    }

    private Response readResponse() throws MyException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Operator login(int id, String password) throws MyException {
        initializeConnection();
        Operator organizator = new Operator(id, password);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(organizator).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            return (Operator) response.data();
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
        return null;
    }

    @Override
    public Boolean usersExist(int id) {
        return null;
    }

    @Override
    public String getPass(int id) {
        return null;
    }

    @Override
    public List<Cursa> getAllCurse() {
        List<Cursa> allProbe = null;
        try {
            Request request = new Request.Builder().type(RequestType.GET_ALL_CURSE).build();
            sendRequest(request);
            Response response = readResponse();

            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new MyException(err);
            }
            allProbe = (List<Cursa>) response.data();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return allProbe;
    }

    @Override
    public List<Participant> getAllParticipanti() {
        List<Participant> allParticipanti = null;
        try {
            Request request = new Request.Builder().type(RequestType.GET_ALL_PARTICIPANTI).build();
            sendRequest(request);
            Response response = readResponse();

            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new MyException(err);
            }
            allParticipanti = (List<Participant>) response.data();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return allParticipanti;
    }

    @Override
    public void saveParticipant(int id, String nume, String echipa, int cap, int idCursa) {
        Participant participantProba = new Participant(id, nume, echipa, cap, idCursa);
        try {
            Request request = new Request.Builder().type(RequestType.ADD_PARTICIPANT_TO_A_CURSA).data(participantProba).build();
            sendRequest(request);
            Response response = readResponse();
            if (response.type() == ResponseType.OK) {
                System.out.println("OK RESPONSE");
            }
            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new MyException(err);
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Participant> getByEchipe(String nume) {
        List<Participant> allParticipanti = null;
        try {
            Request request = new Request.Builder().type(RequestType.GET_BY_ECHIPA).data(nume).build();
            sendRequest(request);
            Response response = readResponse();

            if (response.type() == ResponseType.ERROR) {
                String err = response.data().toString();
                throw new MyException(err);
            }
            allParticipanti = (List<Participant>) response.data();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return allParticipanti;
    }

    @Override
    public void addObserever(IObserver observer) {
        this.client = observer;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
