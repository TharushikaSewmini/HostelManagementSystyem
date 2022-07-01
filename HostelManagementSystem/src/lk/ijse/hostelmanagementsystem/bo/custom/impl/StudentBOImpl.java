package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.StudentDAO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOType.STUDENT);

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
        return studentDAO.update(new Student(
                studentDTO.getSId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getContact(),
                studentDTO.getDob(),
                studentDTO.getGender()
        ));
    }

    @Override
    public boolean delete(String id) throws Exception {
        return studentDAO.delete(id);
    }

    @Override
    public StudentDTO getStudent(String id) throws Exception {
        Student s = studentDAO.get(id);
        return new StudentDTO(s.getSId(), s.getName(), s.getAddress(), s.getContact(), s.getDob(), s.getGender());
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws Exception {
        List<Student> all = studentDAO.getAll();
        ArrayList<StudentDTO> list = new ArrayList<>();
        for (Student student : all) {
            list.add(new StudentDTO(
                    student.getSId(),
                    student.getName(),
                    student.getAddress(),
                    student.getContact(),
                    student.getDob(),
                    student.getGender()
            ));
        }
        return list;
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
    public StudentDTO searchStudent(String student) throws Exception {
        Student s = studentDAO.search(student);
        if (s!=null) {
            return new StudentDTO(s.getSId(), s.getName(), s.getAddress(), s.getContact(), s.getDob(), s.getGender());
        }
        return null;
    }
}
