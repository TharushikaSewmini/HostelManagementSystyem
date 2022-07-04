package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.view.tm.RoomTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public JFXTextField txtRoomTypeId;
    public JFXTextField txtType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtPay;
    public JFXButton btnRegister;
    public JFXButton btnAddReserveRoom;
    public TableView<RoomTM> tblReserveRoom;
    public TableColumn colRoomId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public Label lblReservationId;
    public TableColumn colDelete;

    String studentId;
    String reservationId;

    // Property Injection(DI)
    private final RegistrationBO registrationBO = BOFactory.getInstance().getBO(BOType.REGISTRATION);
    private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);
    private final StudentBO studentBO = BOFactory.getInstance().getBO(BOType.STUDENT);

    public void initialize() {
        initUI();


        tblReserveRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tblReserveRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblReserveRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        TableColumn<RoomTM, Button> lastCol = (TableColumn<RoomTM, Button>) tblReserveRoom.getColumns().get(3);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblReserveRoom.getItems().remove(param.getValue());
                tblReserveRoom.getSelectionModel().clearSelection();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnCheckRoomsAvailability.setDisable(newValue == null);

            if (newValue != null) {
                try {
                    /*Search Room*/
                    RoomDTO search = roomBO.searchRoomType(newValue + "");

                    btnCheckRoomsAvailability.setOnMouseClicked(event -> {
                        if (search.getQty() > 0 ) {
                            new Alert(Alert.AlertType.INFORMATION, newValue + " rooms are available").show();

                            lblReservationId.setDisable(false);
                            //txtRegDate.setDisable(false);
                            txtRoomTypeId.setDisable(false);
                            txtType.setDisable(false);
                            txtKeyMoney.setDisable(false);
                            txtPay.setDisable(false);

                            // Generate new reservation id
                            reservationId = generateNewReservationId();
                            lblReservationId.setText(reservationId);

                            txtRoomTypeId.setText(search.getRoomTypeId());
                            txtType.setText(search.getType());
                            txtKeyMoney.setText(String.valueOf(search.getKeyMoney()));


                            String sId = txtSId.getText();
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

                            btnAddReserveRoom.setDisable(false);

                            btnAddReserveRoom.setOnMouseClicked(event1 -> {

                                String roomTypeId = txtRoomTypeId.getText();
                                String roomType = txtType.getText();
                                double keyMoney = Double.parseDouble(txtKeyMoney.getText());

                                //boolean exists = tblReserveRoom.getItems().stream().anyMatch(detail -> detail.getRoomTypeId().equals(roomTypeId));

                                tblReserveRoom.getItems().add(new RoomTM(roomTypeId, roomType, keyMoney));

                                List<RoomDTO> rooms = (tblReserveRoom.getItems().stream().map(tm -> new RoomDTO(lblReservationId, tm.getRoomTypeId(), tm.getType(), tm.getKeyMoney())).collect(Collectors.toList()));

                                btnRegister.setDisable(false);

                                btnRegister.setOnMouseClicked(event2 -> {

                                    if (btnRegister.getText().equalsIgnoreCase("Register")) {
                                        try {

                                            String reservationId = lblReservationId.getText();
                                            /*String roomTypeId = txtRoomTypeId.getText();
                                            String roomType = txtType.getText();
                                            double keyMoney = Double.parseDouble(txtKeyMoney.getText());*/

                                            int qty = !txtKeyMoney.getText().isEmpty() ? search.getQty() - 1 : search.getQty();


                                            double payment = Double.parseDouble((txtPay.getText()));
                                            double remainKeyMoney = keyMoney - payment;
                                            //int qty = !txtKeyMoney.getText().isEmpty() ? search.getQty() - 1 : search.getQty();
                                            //lblRemainKeyMoney.setText(String.valueOf(keyMoney));

                                            String status = (keyMoney != payment) ? remainKeyMoney + "" + " has to be paid" : "Paid";

                                            try {
                                                roomBO.update(new RoomDTO(roomTypeId, roomType, keyMoney, qty));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            //StudentDTO studentDTO = new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender);

                                            List<RoomDTO> list = roomBO.getAllRoomTypes();
                                            List<RoomDTO> roomDTO = new ArrayList<>();
                                            for (RoomDTO roomList : list) {
                                                roomDTO.add(new RoomDTO(roomList.getRoomTypeId(), roomList.getType(), roomList.getKeyMoney(), roomList.getQty()));
                                            }

                                            registrationBO.addReservation(new ReservationDTO(reservationId, new StudentDTO(sId, name, address, contact, LocalDate.parse(dob), gender), rooms, status));

                                            new Alert(Alert.AlertType.CONFIRMATION, "Registration is Successfull.!").show();

                                            initUI();
                                        } catch (Exception e) {
                                            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                            System.out.println(e);
                                        }
                                    }
                                });

                            });
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
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtPay.clear();

        lblReservationId.setDisable(true);
        txtRoomTypeId.setDisable(true);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtPay.setDisable(true);

        txtRoomTypeId.setEditable(false);
        txtType.setEditable(false);
        txtKeyMoney.setEditable(false);

        txtSId.setEditable(false);
        btnCheckRoomsAvailability.setDisable(true);
        btnAddReserveRoom.setDisable(true);
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

    public void newReservationOnAction(ActionEvent actionEvent) {
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
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtPay.clear();

        //txtReservationId.setDisable(true);
        txtRoomTypeId.setDisable(true);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtPay.setDisable(true);

        txtRoomTypeId.setEditable(false);
        txtType.setEditable(false);
        txtKeyMoney.setEditable(false);

        txtSId.setEditable(false);
        btnCheckRoomsAvailability.setDisable(true);
        btnAddReserveRoom.setDisable(true);
        btnRegister.setDisable(true);
    }
}
