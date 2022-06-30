package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.StudentBOImpl;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.view.tm.StudentTM;

import java.util.ArrayList;

public class StudentManagementFormController {
    public JFXTextField txtSId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXDatePicker txtDob;
    public JFXRadioButton rbtnMale;
    public JFXRadioButton rbtnFemale;
    public JFXButton btnSaveStudent;
    public JFXButton btnAddNewStudent;
    public JFXButton btnDeleteStudent;
    public TableView<StudentTM> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDob;
    public TableColumn colGender;
    public ToggleGroup studentGender;

    String studentId;

    // Property Injection(DI)
    private final StudentBOImpl studentBOImpl = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteStudent.setDisable(newValue == null);
            btnSaveStudent.setText(newValue != null ? "Update" : "Save");
            btnSaveStudent.setDisable(newValue == null);

            if (newValue != null) {
                txtSId.setText(newValue.getSId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                txtDob.setValue(newValue.getDob());
                if (newValue.getGender().equals("Male")) {
                     rbtnMale.setSelected(true);
                } else {
                     rbtnMale.setSelected(true);
                }
            }
        });

        loadAllStudents();

    }

    private void loadAllStudents() {
        tblStudent.getItems().clear();
        /*Get all students*/
        try {
            ArrayList<StudentDTO> allStudents = studentBOImpl.getAllStudents();

            for (StudentDTO student : allStudents) {
                tblStudent.getItems().add(new StudentTM(student.getSId(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtSId.clear();
        studentId = generateNewStudentId();
        txtSId.setText(studentId);
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtDob.setValue(null);
        rbtnMale.setSelected(false);
        rbtnFemale.setSelected(false);
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {
    }

    public void addNewStudentOnAction(ActionEvent actionEvent) {
    }

    public void deleteStudentOnAction(ActionEvent actionEvent) {
    }

    public void setGenderOnAction(ActionEvent actionEvent) {
    }

    public String generateNewStudentId() {
        try {
            return studentBOImpl.generateNewStudentId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new student id").show();
        }
        return "S001";
    }
}
