package model;

public class Participant {
    private final int id;
    private final String nume;
    private final String echipa;
    private final int cap;
    private final int idCursa;

    public Participant(int id, String nume, String echipa, int cap, int idCursa) {
        this.id = id;
        this.nume = nume;
        this.echipa = echipa;
        this.cap = cap;
        this.idCursa = idCursa;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEchipa() {
        return echipa;
    }

    public int getCap() {
        return cap;
    }

    public int getIdCursa() {
        return idCursa;
    }
}
