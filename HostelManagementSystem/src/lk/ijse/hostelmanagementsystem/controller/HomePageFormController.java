package lk.ijse.hostelmanagementsystem.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.view.tm.StudentTM;

import java.util.ArrayList;

public class HomePageFormController {
    public AnchorPane mainContext;
    public Label lblRoomCount;
    public Label lblStudentCount;
    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDob;
    public TableColumn colGender;

    // Property Injection(DI)
    private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);
    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        try {
            lblRoomCount.setText(String.valueOf(roomBO.getRoomCount()));
            lblStudentCount.setText(String.valueOf(studentBO.getStudentCount()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadAllStudents();
    }

    private void loadAllStudents() {
        tblStudent.getItems().clear();
        /*Get all students*/
        try {
            ArrayList<StudentDTO> allStudents = studentBO.getAllStudents();

            for (StudentDTO student : allStudents) {
                tblStudent.getItems().add(new StudentTM(student.getSId(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
