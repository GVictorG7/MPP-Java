package repository;

import java.util.ArrayList;

public interface IRepository<T> {
    int size();

    void save(T entity);

    void delete(int id);

    T findOne(int id);

    ArrayList<T> findAll();
}
