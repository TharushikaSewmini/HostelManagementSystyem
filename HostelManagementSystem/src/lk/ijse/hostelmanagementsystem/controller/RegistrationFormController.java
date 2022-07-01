package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.StudentBOImpl;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
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
    public JFXTextField txtReservationId;

    String studentId;
    String reservationId;

    // Property Injection(DI)
    private final RegistrationBO registrationBO = BOFactory.getInstance().getBO(BOType.REGISTRATION);
    private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);
    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        initUI();

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnCheckRoomsAvailability.setDisable(newValue == null);

            if (newValue != null) {
                try {
                    /*Search Room*/
                    RoomDTO search = roomBO.searchRoomType(newValue + "");

                    btnCheckRoomsAvailability.setOnMouseClicked(event -> {
                        if (search.getQty() > 0 ) {

                            new Alert(Alert.AlertType.INFORMATION, newValue + " rooms are available").show();

                            txtReservationId.setDisable(false);
                            txtRegDate.setDisable(false);
                            txtRoomTypeId.setDisable(false);
                            txtType.setDisable(false);
                            txtKeyMoney.setDisable(false);
                            txtPay.setDisable(false);

                            // Generate new reservation id
                            reservationId = generateNewReservationId();
                            txtReservationId.setText(reservationId);

                            txtRoomTypeId.setText(search.getRoomTypeId());
                            txtType.setText(search.getType());
                            txtKeyMoney.setText(String.valueOf(search.getKeyMoney()));

                            btnRegister.setDisable(false);

                            btnRegister.setOnMouseClicked(event1 -> {

                                String sId = txtSId.getText();
                                String name = txtName.getText();
                                String address = txtAddress.getText();
                                String contact = txtContact.getText();
                                String dob = String.valueOf(txtDob.getValue());
                                String gender = getGender();
                                String roomTypes = cmbRoomType.getValue();

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
                                        studentBO.add(new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender));

                                        String reservationId = txtReservationId.getText();
                                        String roomTypeId = txtRoomTypeId.getText();
                                        String roomType = txtType.getText();
                                        double keyMoney = Double.parseDouble(txtKeyMoney.getText());
                                        int qty = !txtKeyMoney.getText().isEmpty() ? search.getQty() - 1 : search.getQty();

                                        try {
                                            roomBO.update(new RoomDTO(roomTypeId, roomType, keyMoney, qty));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        //tblCustomer.getItems().add(new CustomerTM(custID, custTitle, custName, custAddress, city, province, postalCode));
                                    } catch (Exception e) {
                                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                    }
                                }/* else {
                                    try {

                                        studentBO.update(new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender));

                                    } catch (Exception e) {
                                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                    }
                                }*/

                                //StudentDTO studentDTO = (StudentDTO) studentBO;

                                //RoomDTO roomDTO = (RoomDTO) roomBO;

                            //registrationBO.addReservation(new ReservationDTO(txtReservationId.getText(), txtRegDate.getValue(), studentDTO, roomDTO, txtPay.getText()));

                                new Alert(Alert.AlertType.CONFIRMATION, "Registration is Successfull.!").show();
                            });

                            //initUI();
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
        txtReservationId.clear();
        txtRegDate.setValue(null);
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtPay.clear();

        txtReservationId.setDisable(true);
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
        txtReservationId.setEditable(false);
        btnRegister.setDisable(true);

    }

    private void loadAllRoomTypes() {
        try {
            ArrayList<RoomDTO> all = roomBO.getAllRoomTypes();
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

    public String generateNewStudentId() {
        try {
            return studentBO.generateNewStudentId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new student id").show();
        }
        return "S001";
    }

    public String generateNewReservationId() {
        try {
            return registrationBO.generateNewReservationId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new student id").show();
        }
        return "R001";
    }

}
