package DTO;

import java.io.Serializable;

public class DetailedCursaDTO implements Serializable {
    private int id;
    private int cap;
    private int nrParticipanti;

    public DetailedCursaDTO(int id, int cap, int nrParticipanti) {
        this.id = id;
        this.cap = cap;
        this.nrParticipanti = nrParticipanti;
    }

    public int getCap() {
        return cap;
    }

    public int getId() {
        return id;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }
}
