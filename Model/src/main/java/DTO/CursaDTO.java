package DTO;

import java.io.Serializable;

public class CursaDTO implements Serializable {
    private int id;
    private int cap;

    public CursaDTO(int id, int cap) {
        this.id = id;
        this.cap = cap;
    }

    public int getCap() {
        return cap;
    }

    public int getId() {
        return id;
    }
}
