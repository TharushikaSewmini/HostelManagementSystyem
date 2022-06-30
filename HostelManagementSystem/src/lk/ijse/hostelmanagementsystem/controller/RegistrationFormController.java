package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.StudentBOImpl;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegistrationFormController {
    public JFXTextField txtSId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXDatePicker txtDob;
    public JFXRadioButton rbtnMale;
    public JFXRadioButton rbtnFemale;
    public ToggleGroup gender;
    public JFXComboBox<String> cmbRoomType;
    public JFXButton btnCheckRoomsAvailability;
    public JFXDatePicker txtRegDate;
    public JFXTextField txtRoomTypeId;
    public JFXTextField txtType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtPay;
    public JFXButton btnRegister;

    String studentId;

    // Property Injection(DI)
    private final RegistrationBOImpl registrationBOImpl = BOFactory.getInstance().getBO(BOType.REGISTRATION);
    private final RoomBoImpl roomBOImpl = BOFactory.getInstance().getBO(BOType.ROOM);
    private final StudentBOImpl studentBOImpl = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        initUI();

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnCheckRoomsAvailability.setDisable(newValue == null);

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    RoomDTO search = roomBOImpl.searchRoomType(newValue + "");

                    btnCheckRoomsAvailability.setOnMouseClicked(event -> {
                        if (search.getQty() > 0 ) {

                            new Alert(Alert.AlertType.INFORMATION, newValue + " rooms are available").show();

                            txtRegDate.setDisable(false);
                            txtRoomTypeId.setDisable(false);
                            txtType.setDisable(false);
                            txtKeyMoney.setDisable(false);
                            txtPay.setDisable(false);

                            txtRoomTypeId.setText(search.getRoomTypeId());
                            txtType.setText(search.getType());
                            txtKeyMoney.setText(String.valueOf(search.getKeyMoney()));

                            String roomTypeId = txtRoomTypeId.getText();
                            String roomType = txtType.getText();
                            double keyMoney = Double.parseDouble(txtKeyMoney.getText());
                            int qty = !txtKeyMoney.getText().isEmpty() ? search.getQty() - 1 : search.getQty();

                            try {
                                roomBOImpl.update(new RoomDTO(roomTypeId, roomType, keyMoney, qty));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            btnRegister.setDisable(false);
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, newValue + " rooms are not available").show();
                            }
                        });
                } catch(Exception e){
                    new Alert(Alert.AlertType.ERROR, "Failed to find the type " + newValue + "" + e).show();
                }
            }
        });
        loadAllRoomTypes();
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
        cmbRoomType.getSelectionModel().clearSelection();
        txtRegDate.setValue(null);
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtPay.clear();

        txtRegDate.setDisable(true);
        txtRoomTypeId.setDisable(true);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtPay.setDisable(true);

        txtRegDate.setEditable(false);
        txtRoomTypeId.setEditable(false);
        txtType.setEditable(false);
        txtKeyMoney.setEditable(false);

        txtSId.setEditable(false);
        btnCheckRoomsAvailability.setDisable(true);
        btnRegister.setDisable(true);

    }

    private void loadAllRoomTypes() {
        try {
            ArrayList<RoomDTO> all = roomBOImpl.getAllRoomTypes();
            for (RoomDTO room : all) {
                cmbRoomType.getItems().add(room.getType());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load room types").show();
        }
    }

    private String getGender() {
        if (rbtnMale.isSelected()) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public void setGenderOnAction(ActionEvent actionEvent) {
    }

    public void roomTypeOnAction(ActionEvent actionEvent) {
    }

    public void registerOnAction(ActionEvent actionEvent) {
        String sId = txtSId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String dob = String.valueOf(txtDob.getValue());
        String gender = getGender();
        String roomType = cmbRoomType.getValue();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        } else if (!contact.matches("[0,0-9 ]+")) {
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

        if (btnRegister.getText().equalsIgnoreCase("Register")) {
            try {
                //Save Student
                registrationBOImpl.add(new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender));


                //tblCustomer.getItems().add(new CustomerTM(custID, custTitle, custName, custAddress, city, province, postalCode));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {

                registrationBOImpl.update(new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender));

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        //btnCheckRoomsAvailability.fire();
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
