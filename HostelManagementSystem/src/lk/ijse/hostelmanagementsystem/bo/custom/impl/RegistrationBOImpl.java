package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.ReservationDAO;
import lk.ijse.hostelmanagementsystem.dao.custom.RoomDAO;
import lk.ijse.hostelmanagementsystem.dao.custom.StudentDAO;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.entity.Reservation;
import lk.ijse.hostelmanagementsystem.entity.Room;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOType.STUDENT);
    private final RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);
    private final ReservationDAO reservationDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION);

    @Override
    public boolean update(StudentDTO studentDTO) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public String generateNewReservationId() throws Exception {
        String id = reservationDAO.generateNewID();

        if (id!=null) {
            int newStudentId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R%03d", newStudentId);
        } else {
            return "R001";
        }
    }

    @Override
    public boolean addReservation(ReservationDTO reservationDTO) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            session.beginTransaction();

            StudentDTO studentDTO = reservationDTO.getStudentDTO();
            Student student = new Student(studentDTO.getSId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender());
            List<RoomDTO> list = reservationDTO.getRoomList();
            List<Room> roomList = new ArrayList<>();
            for (RoomDTO dto1 : list) {
                roomList.add(new Room(dto1.getRoomTypeId(), dto1.getType(), dto1.getKeyMoney(), dto1.getQty()));
            }
            if (studentDAO.get(student.getSId()) == null) {
                return studentDAO.add(student) && reservationDAO.add(new Reservation(reservationDTO.getResId(), reservationDTO.getDate(), student, roomList, reservationDTO.getStatus()));
            }
            return reservationDAO.add(new Reservation(reservationDTO.getResId(), reservationDTO.getDate(), student, roomList, reservationDTO.getStatus()));

        } catch (Throwable t) {
            session.getTransaction().rollback();
            throw t;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateReservationStatus(ReservationDTO reservationDTO) throws Exception {
        return reservationDAO.updateStatus(new Reservation(
                reservationDTO.getResId(),
                reservationDTO.getStatus()
        ));

    }
}
