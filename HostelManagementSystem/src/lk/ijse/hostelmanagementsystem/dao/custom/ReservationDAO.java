package lk.ijse.hostelmanagementsystem.dao.custom;

import lk.ijse.hostelmanagementsystem.dao.CrudDAO;
import lk.ijse.hostelmanagementsystem.entity.Reservation;

public interface ReservationDAO extends CrudDAO<Reservation, String> {
    boolean updateStatus(Reservation entity) throws Exception;
}
