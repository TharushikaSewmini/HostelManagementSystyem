package lk.ijse.hostelmanagementsystem.dao.custom;

import lk.ijse.hostelmanagementsystem.dao.CrudDAO;
import lk.ijse.hostelmanagementsystem.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    String getUserName(String id) throws Exception;

}
