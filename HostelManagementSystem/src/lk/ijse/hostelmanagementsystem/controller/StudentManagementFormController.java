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
import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.view.tm.StudentTM;

import java.time.LocalDate;
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
    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

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
                     rbtnFemale.setSelected(true);
                }
            }
        });
        rbtnFemale.setOnAction(event -> btnSaveStudent.fire());
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

        txtSId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtDob.setDisable(true);
        rbtnMale.setDisable(true);
        rbtnFemale.setDisable(true);
        btnSaveStudent.setDisable(true);
        btnDeleteStudent.setDisable(true);
    }

    private String getGender() {
        if (rbtnMale.isSelected()) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {
        String studentId = txtSId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String dob = String.valueOf(txtDob.getValue());
        String gender = getGender();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        } else if (!contact.matches("[0,0-9]{9,10}")) {
            new Alert(Alert.AlertType.ERROR, "Contact should be at least 10 characters long").show();
            txtContact.requestFocus();
            return;
        } else if (dob.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Date Of Birth can not be null").show();
            txtDob.requestFocus();
            return;
        } else if (gender.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select gender").show();
            rbtnMale.requestFocus();
            return;
        }

        if (btnSaveStudent.getText().equalsIgnoreCase("Save")) {
            try {
                //Save Student
                if (studentBO.add(new StudentDTO(studentId, name, address, contact, LocalDate.parse(dob), gender))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                }
                tblStudent.getItems().add(new StudentTM(studentId, name, address, contact, LocalDate.parse(dob), gender));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                /*Update Student*/
                if (studentBO.update(new StudentDTO(studentId, name, address, contact, LocalDate.parse(dob), gender))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
                }
                StudentTM selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
                selectedStudent.setSId(studentId);
                selectedStudent.setName(name);
                selectedStudent.setAddress(address);
                selectedStudent.setContact(contact);
                selectedStudent.setDob(LocalDate.parse(dob));
                selectedStudent.setGender(gender);
                tblStudent.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        btnAddNewStudent.fire();
    }

    public void addNewStudentOnAction(ActionEvent actionEvent) {
        txtSId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtDob.setDisable(false);
        rbtnMale.setDisable(false);
        rbtnFemale.setDisable(false);

        txtSId.clear();
        txtSId.setText(generateNewStudentId());
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtDob.setValue(null);

        txtName.requestFocus();
        btnSaveStudent.setDisable(false);
        btnSaveStudent.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void deleteStudentOnAction(ActionEvent actionEvent) {
        /*Delete Student*/
        String studentId = tblStudent.getSelectionModel().getSelectedItem().getSId();
        try {
            if (studentBO.delete(studentId)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted.! " + studentId).show();
            }
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            tblStudent.getSelectionModel().clearSelection();
            initUI();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the student " + studentId).show();
        }
    }

    public String generateNewStudentId() {
        try {
            return studentBO.generateNewStudentId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new student id").show();
        }
        return "S001";
    }
}
