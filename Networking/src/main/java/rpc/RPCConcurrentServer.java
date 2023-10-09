package rpc;

import service.IService;
import utils.AbsConcurrentServer;

import java.net.Socket;

public class RPCConcurrentServer extends AbsConcurrentServer {
    private final IService service;

    public RPCConcurrentServer(int port, IService service) {
        super(port);
        this.service = service;
        System.out.println("Curse - RPCConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ServerRPCWorker worker = new ServerRPCWorker(service, client);
        return new Thread(worker);
    }
}
