package serverrmi;

import repo.CurseJdbcRepo;
import repo.OperatorJdbcRepo;
import repo.ParticipantJdbcRepo;
import service.IService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
        Properties props = PropertiesConfiguration.getInstance().getProps();
        OperatorJdbcRepo operatorRepo = new OperatorJdbcRepo(props);
        CurseJdbcRepo curseRepo = new CurseJdbcRepo(props);
        ParticipantJdbcRepo participantRepo = new ParticipantJdbcRepo(props);

        try {
            Server obj = new Server(operatorRepo, curseRepo, participantRepo);
            IService stub = (IService) UnicastRemoteObject.exportObject(obj, 6666);
            Registry registry = LocateRegistry.createRegistry(6666);
            registry.bind("IService", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
