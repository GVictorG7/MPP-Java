package model;

public class Operator {
    private final int id;
    private final String name;
    private final String pass;

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
