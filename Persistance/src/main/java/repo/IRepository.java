package repo;

import java.util.List;

public interface IRepository<T> {
    int size();

    void save(T entity);

    void delete(int id);

    T findOne(int id);

    List<T> findAll();
}
