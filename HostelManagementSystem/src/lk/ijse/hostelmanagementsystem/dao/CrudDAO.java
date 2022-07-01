package lk.ijse.hostelmanagementsystem.dao;

import lk.ijse.hostelmanagementsystem.entity.SuperEntity;

import java.util.List;

public interface CrudDAO <T extends SuperEntity, ID> extends SuperDAO {
    public boolean add(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(ID id) throws Exception;

    public T get(ID id) throws Exception;

    public List<T> getAll() throws Exception;

    String generateNewID() throws Exception;

    T search(ID id) throws Exception;
}
