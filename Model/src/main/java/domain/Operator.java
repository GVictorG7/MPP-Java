package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.rmi.Remote;

@Entity
@Table(name = "Operatori")
public class Operator implements Serializable, Remote {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private String pass;

    public Operator() {
    }

    public Operator(int id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public Operator(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public Operator(int id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
