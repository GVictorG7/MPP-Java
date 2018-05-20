package rpc;

import service.IService;
import utils.AbsConcurrentServer;

import java.net.Socket;

public class RPCConcurrentServer extends AbsConcurrentServer {
    private IService service;

    public RPCConcurrentServer(int port, IService service) {
        super(port);
        this.service = service;
        System.out.println("Curse - RPCConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ServerRPCWorker worker = new ServerRPCWorker(service, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
