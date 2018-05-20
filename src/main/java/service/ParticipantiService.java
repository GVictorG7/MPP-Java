package service;

import model.Participant;
import repository.ParticipantJdbcRepo;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantiService implements Observable<Participant> {
    private ArrayList<Observer<Participant>> participantObserver = new ArrayList<>();
    private ParticipantJdbcRepo repo;

    public ParticipantiService(Properties properties) {
        repo = new ParticipantJdbcRepo(properties);
    }

    public int numByCursa(int id) {
        return repo.numByCursa(id);
    }

    public void save(Participant participant) {
        repo.save(participant);
        ArrayList<Participant> p = new ArrayList<>();
        repo.findAll().forEach(p::add);
        ListEvent<Participant> ev = createEvent(ListEventType.ADD, participant, p);
        notifyObservers(ev);
    }

    public ArrayList<Participant> getParticipanti() {
        return repo.findAll();
    }

    public ArrayList<Participant> getByEchipa(String echipa) {
        return repo.getParticipantiByEchipa(echipa);
    }

    public int size() {
        return repo.size();
    }

    @Override
    public void addObserver(Observer<Participant> o) {
        participantObserver.add(o);
    }

    @Override
    public void notifyObservers(ListEvent<Participant> event) {
        participantObserver.forEach(x -> x.notifyEvent(event));
    }

    private <E> ListEvent<E> createEvent(ListEventType type, final E elem, final List<E> l) {
        return new ListEvent<E>(type) {
            @Override
            public List<E> getList() {
                return l;
            }

            @Override
            public E getElement() {
                return elem;
            }
        };
    }
}
