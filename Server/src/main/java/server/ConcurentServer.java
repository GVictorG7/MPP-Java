//package server;
//
//import utils.AbsConcurrentServer;
//
//import java.net.Socket;
//
//public class ConcurentServer extends AbsConcurrentServer {
//    private ServerImplementation server;
//
//    public ConcurentServer(int port, ServerImplementation server) {
//        super(port);
//        this.server = server;
//    }
//
//    @Override
//    protected Thread createWorker(Socket client) {
//        WorkerRPC worker = new WorkerRPC(server, client);
//        Thread tw = new Thread(worker);
//        return tw;
//    }
//
//}
