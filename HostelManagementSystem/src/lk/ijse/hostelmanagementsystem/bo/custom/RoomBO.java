package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    boolean add(RoomDTO roomDTO) throws Exception;

    boolean update(RoomDTO roomDTO) throws Exception;

    boolean delete(String id) throws Exception;

    ArrayList<RoomDTO> getAllRoomTypes() throws Exception;


    boolean roomExist(String id) throws SQLException, ClassNotFoundException;


    String generateNewRoomId() throws Exception;

    RoomDTO searchRoomType(String type) throws Exception;

    BigInteger getRoomCount() throws Exception;
}
