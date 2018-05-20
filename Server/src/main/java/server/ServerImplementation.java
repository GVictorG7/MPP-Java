//package server;
//
//import DTO.ListDTO;
//import DTO.OperatorDTO;
//import DTO.ParticipantDTO;
//import domain.Participant;
//import repo.CurseJdbcRepo;
//import repo.OperatorJdbcRepo;
//import repo.ParticipantJdbcRepo;
//import utils.IObserver;
//import utils.IServer;
//import utils.ServerException;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class ServerImplementation implements IServer {
//    private final int defaultThreadsNo = 5;
//    private CurseJdbcRepo curseRepo;
//    private OperatorJdbcRepo operatorRepo;
//    private ParticipantJdbcRepo participantRepo;
//    private Map<String, IObserver> loggedClients;
//
//    public ServerImplementation(CurseJdbcRepo curseRepo, OperatorJdbcRepo operatorRepo, ParticipantJdbcRepo participantRepo) {
//        this.curseRepo = curseRepo;
//        this.operatorRepo = operatorRepo;
//        this.participantRepo = participantRepo;
//        loggedClients = new ConcurrentHashMap<>();
//    }
//
//    public synchronized void login(OperatorDTO user, IObserver observer) throws ServerException {
//        try {
//            boolean isUser = operatorRepo.findOne(user.getId()).getPass().equals(user.getPass());
//            if (!isUser) throw new Exception();
//            loggedClients.put(user.getName(), observer);
//        } catch (Exception e) {
//            throw new ServerException("Authentication falied");
//        }
//    }
//
//    public synchronized void register(ParticipantDTO participantDTO) {
//        participantRepo.save(new Participant(participantDTO.getId(), participantDTO.getNume(), participantDTO.getEchipa(), participantDTO.getCap(), participantDTO.getIdCursa()));
//        notifyRegistration(participantDTO);
//    }
//
//    public synchronized ListDTO<ParticipantDTO> getParticipantList() {
//        ListDTO<ParticipantDTO> all = new ListDTO<>();
//        for (Participant p : participantRepo.findAll()) {
//            all.add(new ParticipantDTO(p.getId(), p.getNume(), p.getEchipa(), p.getCap(), p.getIdCursa()));
//        }
//        return all;
//    }
//
//    public synchronized ListDTO<ParticipantDTO> getParticipantListByEchipa(String echipa) {
//        ListDTO<ParticipantDTO> all = new ListDTO<>();
//        for (Participant p : participantRepo.findAll()) {
//            if (p.getEchipa().equals(echipa))
//                all.add(new ParticipantDTO(p.getId(), p.getNume(), p.getEchipa(), p.getCap(), p.getIdCursa()));
//        }
//        return all;
//    }
//
//    @Override
//    public synchronized void notifyRegistration(ParticipantDTO participantDTO) {
//        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
//        for (IObserver observer : loggedClients.values()) {
//            executor.execute(() -> observer.refreshCurse(participantDTO));
//        }
//        executor.shutdown();
//    }
//
//}
