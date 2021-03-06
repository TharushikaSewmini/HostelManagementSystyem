package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.RoomDAO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.entity.Room;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBoImpl implements RoomBO {
    private final RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);

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

    public RoomDTO searchRoomType(String type) throws Exception {
        Room room = roomDAO.search(type);
        if (room!=null) {
            return new RoomDTO(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty());
        }
        return null;
    }

    public ArrayList<RoomDTO> getAllRoomTypes() throws Exception {
        List<Room> all = roomDAO.getAll();
        ArrayList<RoomDTO> list = new ArrayList<>();
        for (Room room : all) {
            list.add(new RoomDTO(
                    room.getRoomTypeId(),
                    room.getType(),
                    room.getKeyMoney(),
                    room.getQty()));
        }
        return list;
    }

    @Override
    public BigInteger getRoomCount() throws Exception {
        return roomDAO.getRoomCount();
    }
}
