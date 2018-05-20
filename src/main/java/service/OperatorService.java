package service;

import model.Operator;
import repository.OperatorJdbcRepo;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OperatorService implements Observable<Operator> {
    private ArrayList<Observer<Operator>> operatorObserver = new ArrayList<>();
    private OperatorJdbcRepo repo;

    public OperatorService(Properties properties) {
        repo = new OperatorJdbcRepo(properties);
    }

    public boolean login(int id, String pass) {
        return repo.findOne(id).getPass().equals(pass);
    }

    @Override
    public void addObserver(Observer<Operator> o) {
        operatorObserver.add(o);
    }

    @Override
    public void notifyObservers(ListEvent<Operator> event) {
        operatorObserver.forEach(x -> x.notifyEvent(event));
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
