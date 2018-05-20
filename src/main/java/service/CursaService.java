package service;

import model.Cursa;
import repository.CurseJdbcRepo;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaService implements Observable<Cursa> {
    private ArrayList<Observer<Cursa>> cursaObserver = new ArrayList<>();
    private CurseJdbcRepo repo;

    public CursaService(Properties properties) {
        repo = new CurseJdbcRepo(properties);
    }

    public ArrayList<Cursa> getCurseByCap(int cap) {
        return repo.getCurseByCap(cap);
    }

    public ArrayList<Cursa> getCurse() {
        return repo.findAll();
    }

    public ArrayList<Integer> getCap() {
        ArrayList<Integer> rez = new ArrayList<>();
        for (Cursa c : getCurse()) {
            if (!rez.contains(c.getCap())) rez.add(c.getCap());
        }
        return rez;
    }

    @Override
    public void addObserver(Observer<Cursa> o) {
        cursaObserver.add(o);
    }

    @Override
    public void notifyObservers(ListEvent<Cursa> event) {
        cursaObserver.forEach(x -> x.notifyEvent(event));
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
