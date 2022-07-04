package lk.ijse.hostelmanagementsystem.dao.custom;

import lk.ijse.hostelmanagementsystem.dao.CrudDAO;
import lk.ijse.hostelmanagementsystem.entity.Student;

import java.math.BigInteger;

public interface StudentDAO extends CrudDAO<Student, String> {
    BigInteger getStudentCount() throws Exception;
}
