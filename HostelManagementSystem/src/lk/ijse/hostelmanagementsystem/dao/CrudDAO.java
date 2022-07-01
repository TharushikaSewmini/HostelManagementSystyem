package lk.ijse.hostelmanagementsystem.dao;

import lk.ijse.hostelmanagementsystem.entity.SuperEntity;

import java.util.List;

public interface CrudDAO <T extends SuperEntity, ID> extends SuperDAO {
    boolean add(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(ID id) throws Exception;

    T get(ID id) throws Exception;

    List<T> getAll() throws Exception;

    String generateNewID() throws Exception;

    T search(ID id) throws Exception;
}
