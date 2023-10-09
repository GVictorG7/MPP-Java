package model;

public class Cursa {
    private final int id;
    private final int cap;

    public Cursa(int id, int cap) {
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
