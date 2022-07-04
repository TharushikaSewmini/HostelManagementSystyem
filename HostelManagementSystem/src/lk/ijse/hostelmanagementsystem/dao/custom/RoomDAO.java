package lk.ijse.hostelmanagementsystem.dao.custom;

import lk.ijse.hostelmanagementsystem.dao.CrudDAO;
import lk.ijse.hostelmanagementsystem.entity.Room;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface RoomDAO extends CrudDAO<Room, String> {
    BigInteger getRoomCount() throws Exception;
}
