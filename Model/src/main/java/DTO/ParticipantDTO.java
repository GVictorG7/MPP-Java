package DTO;

import java.io.Serializable;

public class ParticipantDTO implements Serializable {
    private int id;
    private String nume;
    private String echipa;
    private int cap;
    private int idCursa;

    public ParticipantDTO(int id, String nume, String echipa, int cap, int idCursa) {
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
