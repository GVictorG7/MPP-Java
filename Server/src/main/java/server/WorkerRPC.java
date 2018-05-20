//package server;
//
//import DTO.DetailedCursaDTO;
//import DTO.ParticipantDTO;
//import rpc.Request;
//import rpc.Response;
//import rpc.ResponseType;
//import utils.IObserver;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.Socket;
//
//public class WorkerRPC implements Runnable, IObserver {
//    private ServerImplementation server;
//    private Socket connection;
//    private ObjectInputStream input;
//    private ObjectOutputStream output;
//    private volatile boolean connected;
//
//    public WorkerRPC(ServerImplementation server, Socket connection) {
//        this.server = server;
//        this.connection = connection;
//        try {
//            output = new ObjectOutputStream(connection.getOutputStream());
//            output.flush();
//            input = new ObjectInputStream(connection.getInputStream());
//            connected = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        while (connected) {
//            try {
//                Request request = (Request) input.readObject();
//                Response response = doRequest(request);
//                if (response != null)
//                    sendResponse(response);
//                Thread.sleep(1000);
//            } catch (IOException | ClassNotFoundException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            input.close();
//            output.close();
//            connection.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sendResponse(Response response) throws IOException {
//        output.writeObject(response);
//        output.flush();
//    }
//
//    private Response doRequest(Request request) {
//        Response response = null;
//        String methodName = "do" + request.getType().name();
//        response = executeMethod(methodName, request);
//        if (response != null)
//            System.out.println("Method" + methodName + " executed");
//        return response;
//    }
//
//    private Response executeMethod(String methodName, Request request) {
//        try {
//            Method method = this.getClass().getDeclaredMethod(methodName, Request.class);
//            return (Response) method.invoke(this, request);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void refreshCurse(ParticipantDTO participantDTO) {
//        Response response = new Response(ResponseType.REGISTERED, participantDTO);
//        try {
//            sendResponse(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
