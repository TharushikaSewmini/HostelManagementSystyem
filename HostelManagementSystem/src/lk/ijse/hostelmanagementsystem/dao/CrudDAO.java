package lk.ijse.hostelmanagementsystem.dao;

import lk.ijse.hostelmanagementsystem.entity.SuperEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO <T, ID> extends SuperDAO {
    public boolean add(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(ID id) throws Exception;

    public T find(ID id) throws Exception;

    public List<T> findAll() throws Exception;

    String generateNewID() throws Exception;
}
