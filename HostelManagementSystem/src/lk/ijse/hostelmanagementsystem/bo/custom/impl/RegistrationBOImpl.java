package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.ReservationDAO;
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
    private final ReservationDAO reservationDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION);

    //private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean add(StudentDTO studentDTO) throws Exception {
        return studentDAO.add(new Student(
                studentDTO.getSId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getContact(),
                studentDTO.getDob(),
                studentDTO.getGender()
        ));
    }

    @Override
    public boolean update(StudentDTO studentDTO) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public ArrayList<StudentDTO> getAllRoomTypes() throws Exception {
        return null;
    }

    @Override
    public String generateNewStudentId() throws Exception {
        String id = studentDAO.generateNewID();

        if (id!=null) {
            int newStudentId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S%03d", newStudentId);
        } else {
            return "S001";
        }
    }

    @Override
    public RoomDTO searchRoomType(String type) throws Exception {
        return null;
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
//                return studentDAO.save(student) && registerDAO.save(new Registration( dto.getRegDate(), dto.getRegFee(), student, course_list));

            }
            return reservationDAO.add(new Reservation(reservationDTO.getResId(), reservationDTO.getDate(), student, roomList, reservationDTO.getStatus()));
//            return registerDAO.save(new Registration(dto.getRegDate(), dto.getRegFee(), student, course_list));

        } catch (Throwable t) {
            session.getTransaction().rollback();
            throw t;
        } finally {
            session.close();
        }
    }
}
