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
import java.rmi.RemoteException;
import java.util.List;

public class ServerRPCWorker implements Runnable, IObserver {
    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
    private IService service;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ServerRPCWorker(IService service, Socket connection) {
        this.service = service;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;

        if (request.type() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.type());
            System.out.println(request.data());
            Operator operator = (Operator) request.data();
            try {
                Operator operatorResponse = service.login(operator.getId(), operator.getPass());
                service.addObserever(this);
                return new Response.Builder().type(ResponseType.OK).data(operatorResponse).build();
            } catch (MyException | RemoteException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_ALL_CURSE) {
            System.out.println("Get all curse request");
            List<Cursa> allProbe;
            try {
                allProbe = service.getAllCurse();
                if (allProbe == null) {
                    throw new MyException("Nu exista curse");
                }
            } catch (MyException | RemoteException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
            return new Response.Builder().type(ResponseType.GET_ALL_CURSE).data(allProbe).build();
        }

        if (request.type() == RequestType.GET_ALL_PARTICIPANTI) {
            System.out.println("Get all participant request");
            List<Participant> allParticipant;
            try {
                allParticipant = service.getAllParticipanti();
                if (allParticipant == null) {
                    throw new MyException("Nu participat ");
                }
            } catch (MyException | RemoteException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
            return new Response.Builder().type(ResponseType.GET_ALL_PARTICIPANTI).data(allParticipant).build();
        }

//        if (request.type() == RequestType.GET_ALL_ECHIPE) {
//            System.out.println("Get all echipe request");
//            List<Echipe> allParticipant;
//            try {
//                allParticipant = service.getAllEchipe();
//                if (allParticipant == null) {
//                    throw new MyException("Nu exista echipe");
//                }
//            } catch (MyException e) {
//                connected = false;
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//            return new Response.Builder().type(ResponseType.GET_ALL_EHIPE).data(allParticipant).build();
//        }

        if (request.type() == RequestType.GET_BY_ECHIPA) {
            System.out.println("Get all echipe request");
            List<Participant> allParticipant;
            String nume = (String) request.data();
            try {
                allParticipant = service.getByEchipe(nume);
                if (allParticipant == null) {
                    throw new MyException("Nu exista echipe");
                }
            } catch (MyException | RemoteException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
            return new Response.Builder().type(ResponseType.GET_ALL_EHIPE).data(allParticipant).build();
        }

        if (request.type() == RequestType.ADD_PARTICIPANT_TO_A_CURSA) {
            System.out.println("Add participant to a proba request");
            Participant participantProba = (Participant) request.data();
            try {
                service.saveParticipant(participantProba.getId(), participantProba.getNume(), participantProba.getEchipa(), participantProba.getCap(), participantProba.getIdCursa());
            } catch (RemoteException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
            return new Response.Builder().type(ResponseType.OK).build();
        }

//        if (request.type()==RequestType.LOGOUT){
//            System.out.println("Logout request");
//           // LogoutRequest logReq=(LogoutRequest)request;
//            UserDTO udto=(UserDTO)request.data();
//            User user=DTOUtils.getFromDTO(udto);
//            try {
//                server.logout(user, this);
//                connected=false;
//                return okResponse;
//
//            } catch (MyException e) {
//                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//            }
//        }

        return response;
    }


    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void update() {
        try {
            sendResponse(new Response.Builder().type(ResponseType.ADD_PARTICIPANT_TO_A_CURSA).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
