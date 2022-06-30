package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    public boolean add(RoomDTO roomDTO) throws Exception;

    public boolean update(RoomDTO roomDTO) throws Exception;

    public boolean delete(String id) throws Exception;

    ArrayList<RoomDTO> getAllRoomTypes() throws Exception;


    boolean roomExist(String id) throws SQLException, ClassNotFoundException;


    String generateNewRoomId() throws Exception;

    RoomDTO searchRoomType(String type) throws Exception;
}
