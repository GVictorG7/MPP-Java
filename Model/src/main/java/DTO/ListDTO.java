package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ListDTO<E extends Serializable> implements Serializable {

    private ArrayList<E> transferList;

    public ListDTO() {
        transferList = new ArrayList<>();
    }

    public void add(E element) {
        transferList.add(element);
    }

    public ArrayList<E> toArray() {
        return transferList;
    }

    public int size() {
        return transferList.size();
    }

    public boolean isEmpty() {
        return transferList.isEmpty();
    }

    public Iterable<E> getIterable() {
        return transferList;
    }

    public void remove(E element) {
        transferList.remove(element);
    }

    public boolean contains(E element) {
        return transferList.contains(element);
    }
}
