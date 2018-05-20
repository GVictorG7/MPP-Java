package DTO;

import java.io.Serializable;

public class OperatorDTO implements Serializable {
    private int id;
    private String name;
    private String pass;

    public OperatorDTO(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public OperatorDTO(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public OperatorDTO(int id, String pass) {
        this.id = id;
        //this.name = name;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
