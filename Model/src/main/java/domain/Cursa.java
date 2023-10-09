package domain;

import java.io.Serializable;
import java.rmi.Remote;

public class Cursa implements Serializable, Remote {
    private final int id;
    private final int cap;
    private final int nrPart;

    public Cursa(int id, int cap, int nrPart) {
        this.id = id;
        this.cap = cap;
        this.nrPart = nrPart;
    }

    public int getCap() {
        return cap;
    }

    public int getId() {
        return id;
    }

    public int getNrPart() {
        return nrPart;
    }
}
