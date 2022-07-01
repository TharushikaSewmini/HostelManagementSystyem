package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.UserBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.UserDAO;
import lk.ijse.hostelmanagementsystem.dto.UserDTO;
import lk.ijse.hostelmanagementsystem.entity.User;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOType.USER);

    @Override
    public boolean add(UserDTO userDTO) throws Exception {
        return userDAO.add(new User(
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword()
        ));
    }

    @Override
    public boolean update(UserDTO user) throws Exception {
        return userDAO.update(new User(
                user.getUserId(),
                user.getUserName(),
                user.getPassword()
        ));
    }

     @Override
     public String getUserName(String id) throws Exception {
        String userName = userDAO.getUserName(id);
        return userName;
     }

    @Override
    public UserDTO search(String id) throws Exception {
        User user = userDAO.search(id);
        if (user!=null) {
            return new UserDTO(user.getUserId(), user.getUserName(), user.getPassword());
        }
        return null;
    }
}
