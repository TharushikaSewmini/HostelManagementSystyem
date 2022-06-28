package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.RoomDAO;
import lk.ijse.hostelmanagementsystem.dao.custom.impl.RoomDAOImpl;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.entity.Room;

import java.sql.SQLException;

public class RoomBoImpl implements RoomBO {
    //RoomBoImpl customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);

    //CustomerDAOImpl customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    private final RoomDAOImpl roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);

    @Override
    public boolean add(RoomDTO roomDTO) throws Exception {
        return roomDAO.add(new Room(
                roomDTO.getRoomTypeId(),
                roomDTO.getType(),
                roomDTO.getKeyMoney(),
                roomDTO.getQty()
        ));
    }

    @Override
    public boolean update(RoomDTO roomDTO) throws Exception {
        return roomDAO.update(new Room(
                roomDTO.getRoomTypeId(),
                roomDTO.getType(),
                roomDTO.getKeyMoney(),
                roomDTO.getQty()
        ));
    }

    @Override
    public boolean delete(String id) throws Exception {
        return roomDAO.delete(id);
    }

    @Override
    public String generateNewRoomId() throws Exception {
        String id = roomDAO.generateNewID();

        if (id!=null) {
            int newCustomerId = Integer.parseInt(id.replace("RM", "")) + 1;
            return String.format("RM%03d", newCustomerId);
        } else {
            return "RM-001";
        }
    }

    @Override
    public boolean roomExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
