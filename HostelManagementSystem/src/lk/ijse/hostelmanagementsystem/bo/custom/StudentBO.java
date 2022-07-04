package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.CustomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;

import java.math.BigInteger;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    ArrayList<CustomDTO> getStudentNotPaidKeyMoney() throws Exception;

    boolean add(StudentDTO studentDTO) throws Exception;

    boolean update(StudentDTO studentDTO) throws Exception;

    boolean delete(String id) throws Exception;

    StudentDTO getStudent(String id) throws Exception;

    ArrayList<StudentDTO> getAllStudents() throws Exception;

    String generateNewStudentId() throws Exception;

    StudentDTO searchStudent(String student) throws Exception;

    BigInteger getStudentCount() throws Exception;
}
