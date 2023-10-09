package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private final int port;
    private ServerSocket server = null;

    AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for clients ...");
                Socket client = server.accept();
                System.out.println("Client connected");
                processRequest(client);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServerException("Starting server errror", ex);
        } finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    private void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
