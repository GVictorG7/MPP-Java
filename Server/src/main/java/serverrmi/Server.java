package serverrmi;

import domain.Cursa;
import domain.Operator;
import domain.Participant;
import repo.CurseJdbcRepo;
import repo.OperatorJdbcRepo;
import repo.ParticipantJdbcRepo;
import service.IObserver;
import service.IService;
import service.MyException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements IService, Serializable {
    private final OperatorJdbcRepo operatorRepo;
    private final CurseJdbcRepo curseRepo;
    private final ParticipantJdbcRepo participantRepo;
    private final Map<Integer, IObserver> loggedOperators;

    public Server(OperatorJdbcRepo operatorRepo, CurseJdbcRepo curseRepo, ParticipantJdbcRepo participantRepo) {
        this.operatorRepo = operatorRepo;
        this.curseRepo = curseRepo;
        this.participantRepo = participantRepo;

        loggedOperators = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Operator login(int id, String password) throws MyException {
        Operator operator = operatorRepo.findOne(id);

        if (operator == null) {
            throw new MyException("Nu esista User-ul cu id-ul" + id);
        }
        if (!operator.getPass().equals(password)) {
            throw new MyException("Parola gresita");
        }
        if (loggedOperators.get(operator.getId()) != null) {
            throw new MyException("Organizator deja logat");
        }
        return operator;
    }

    public Boolean usersExist(int id) {
        for (Operator user : operatorRepo.findAll()) {
            if (user.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public String getPass(int id) {
        for (Operator user1 : operatorRepo.findAll()) {
            if (user1.getId() == id)
                return user1.getPass();
        }
        return null;
    }

    @Override
    public synchronized List<Cursa> getAllCurse() {
        return curseRepo.findAll();
    }

    @Override
    public synchronized List<Participant> getAllParticipanti() {
        return participantRepo.findAll();
    }

    @Override
    public synchronized void saveParticipant(int id, String nume, String echipa, int cap, int idCursa) {
        participantRepo.save(new Participant(id, nume, echipa, cap, idCursa));
        notifyMyObservers();
    }

    @Override
    public synchronized List<Participant> getByEchipe(String echipa) {
        return participantRepo.getParticipantiByEchipa(echipa);
    }

    @Override
    public void addObserever(IObserver observer) {
        loggedOperators.put(1, observer);
    }

    private void notifyMyObservers() {
        loggedOperators.values().forEach(IObserver::update);
    }
}
