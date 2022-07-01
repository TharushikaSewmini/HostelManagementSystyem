package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.UserDTO;

public interface UserBO extends SuperBO {
    boolean add(UserDTO userDTO) throws Exception;

    boolean update(UserDTO userDTO) throws Exception;

    String getUserName(String id) throws Exception;

    UserDTO search(String id) throws Exception;
}
