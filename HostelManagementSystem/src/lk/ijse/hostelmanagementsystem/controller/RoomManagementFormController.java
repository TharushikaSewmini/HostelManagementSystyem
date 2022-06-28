package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.view.tm.RoomTM;

import java.sql.SQLException;

public class RoomManagementFormController {
    public JFXTextField txtRoomId;
    public JFXTextField txtType;
    public JFXTextField txtQty;
    public JFXTextField txtKeyMoney;
    public TableView<RoomTM> tblRoom;
    public TableColumn colRoomId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public JFXButton btnSaveRoom;
    public JFXButton btnDeleteRoom;
    public JFXButton btnAddNewRoom;

    // Property Injection(DI)
    private final RoomBoImpl roomBOImpl = BOFactory.getInstance().getBO(BOType.ROOM);

    //CustomerBOImpl customerBOImpl = BOFactory.getInstance().getBO(BOType.CUSTOMER);

    public void initialize() {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        //colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteRoom.setDisable(newValue == null);
            btnSaveRoom.setText(newValue != null ? "Update" : "Save");
            btnSaveRoom.setDisable(newValue == null);

            if (newValue != null) {
                txtRoomId.setText(newValue.getRoomTypeId());
                txtType.setText(newValue.getType());
                //txtQty.setText(newValue.getPackSize());
                txtKeyMoney.setText(String.valueOf(newValue.getKeyMoney()));
                txtQty.setText(String.valueOf(newValue.getQty()));

                txtRoomId.setDisable(false);
                txtType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
                //txtQtyOnHand.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnSaveRoom.fire());
        loadAllRooms();
    }

    private void loadAllRooms() {
        tblRoom.getItems().clear();
        /*Get all rooms*/
        /*try {
            ArrayList<RoomDTO> allRooms = roomBO.get();
            for (RoomDTO room : allRooms) {
                tblRoom.getItems().add(new RoomTM(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/
    }

    private void initUI() {
        txtRoomId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        //txtQtyOnHand.clear();
        txtRoomId.setDisable(true);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);
        //txtQtyOnHand.setDisable(true);
        txtRoomId.setEditable(false);
        btnSaveRoom.setDisable(true);
        btnDeleteRoom.setDisable(true);
    }

    public void saveRoomOnAction(ActionEvent actionEvent) {
        String roomTypeId = txtRoomId.getText();
        String type = txtType.getText();

        /*String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        try {
            if (customerBOImpl.add(new CustomerDTO(id, name, address))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                clearFields();
            }
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }*/



        if (!type.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid type").show();
            txtType.requestFocus();
            return;
        } else if (!txtKeyMoney.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid key money").show();
            txtKeyMoney.requestFocus();
            return;
        } else if (!txtQty.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            return;
        }

        double keyMoney = Double.parseDouble(txtKeyMoney.getText());
        int qty = Integer.parseInt(txtQty.getText());




        if (btnSaveRoom.getText().equalsIgnoreCase("save")) {
            try {
                /*if (existRoom(roomTypeId)) {
                    new Alert(Alert.AlertType.ERROR, roomTypeId + " already exists").show();
                }
                //Save Customer
                roomBO.add(new RoomDTO(roomTypeId, type, keyMoney, qty));*/


                if (roomBOImpl.add(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                    //clearFields();
                }



                tblRoom.getItems().add(new RoomTM(roomTypeId, type, keyMoney, qty));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {

                /*if (!existRoom(roomTypeId)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + roomTypeId).show();
                }
                /*Update Customer*/
                /*roomBO.update(new RoomDTO(roomTypeId, type, keyMoney, qty));*/

                if (roomBOImpl.update(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
                    //clearFields();
                }

                RoomTM selectedItem = tblRoom.getSelectionModel().getSelectedItem();
                selectedItem.setType(type);
                selectedItem.setKeyMoney(keyMoney);
                selectedItem.setQty(qty);
                tblRoom.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btnAddNewRoom.fire();

    }

    public void deleteRoomOnAction(ActionEvent actionEvent) {
        /*Delete Room*/
        String roomTypeId = tblRoom.getSelectionModel().getSelectedItem().getRoomTypeId();
        try {
            if (roomBOImpl.delete(roomTypeId)) {
                new Alert(Alert.AlertType.ERROR, "Deleted.! " + roomTypeId).show();
            }

            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            tblRoom.getSelectionModel().clearSelection();
            initUI();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the room " + roomTypeId).show();
        }
    }

    public void addNewRoomOnAction(ActionEvent actionEvent) {
        txtRoomId.setDisable(false);
        txtType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        //txtQtyOnHand.setDisable(false);
        txtRoomId.clear();
        txtRoomId.setText(generateNewId());
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        //txtQtyOnHand.clear();
        txtType.requestFocus();
        btnSaveRoom.setDisable(false);
        btnSaveRoom.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return roomBOImpl.generateNewRoomId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "R001";

        /*String s = null;
        try {
            s = roomBOImpl.generateNewRoomId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;*/
    }

    /*private boolean existRoom(String roomTypeTd) throws SQLException, ClassNotFoundException {
        //return roomBO.roomExist(roomTypeTd);
    }*/

}
