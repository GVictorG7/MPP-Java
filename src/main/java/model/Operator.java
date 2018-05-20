package model;

public class Operator {
    private int id;
    private String name;
    private String pass;

    public Operator(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }
}
