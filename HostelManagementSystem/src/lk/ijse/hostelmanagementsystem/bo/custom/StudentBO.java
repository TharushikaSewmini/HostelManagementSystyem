package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;

import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    public boolean add(StudentDTO studentDTO) throws Exception;

    public boolean update(StudentDTO studentDTO) throws Exception;

    public boolean delete(String id) throws Exception;

    ArrayList<StudentDTO> getAllStudents() throws Exception;

    String generateNewStudentId() throws Exception;

    StudentDTO searchStudent(String student) throws Exception;
}
