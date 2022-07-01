package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;

import java.util.ArrayList;

public interface RegistrationBO extends SuperBO {
    public boolean add(StudentDTO studentDTO) throws Exception;

    public boolean update(StudentDTO studentDTO) throws Exception;

    public boolean delete(String id) throws Exception;

    ArrayList<StudentDTO> getAllRoomTypes() throws Exception;

    String generateNewStudentId() throws Exception;

    RoomDTO searchRoomType(String type) throws Exception;

    String generateNewReservationId() throws Exception;

    boolean addReservation(ReservationDTO reservationDTO) throws Exception;

    //boolean addReservation(ReservationDTO reservationDTO) throws Exception;
}
