package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.impl.StudentDAOImpl;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.entity.Student;

import java.util.ArrayList;

public class RegistrationBOImpl implements RegistrationBO {

    private final StudentDAOImpl studentDAO = DAOFactory.getInstance().getDAO(DAOType.STUDENT);

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
}
