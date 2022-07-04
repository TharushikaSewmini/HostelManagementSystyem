package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;

import java.util.ArrayList;

public interface RegistrationBO extends SuperBO {
    boolean update(StudentDTO studentDTO) throws Exception;

    boolean delete(String id) throws Exception;

    String generateNewReservationId() throws Exception;

    boolean addReservation(ReservationDTO reservationDTO) throws Exception;

    boolean updateReservationStatus(ReservationDTO reservationDTO) throws Exception;
}
