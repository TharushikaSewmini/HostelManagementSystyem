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
import lk.ijse.hostelmanagementsystem.bo.custom.RoomBO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.view.tm.RoomTM;

import java.util.ArrayList;

public class RoomManagementFormController {
    public JFXTextField txtRoomTypeId;
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
    private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);

    public void initialize() {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteRoom.setDisable(newValue == null);
            btnSaveRoom.setText(newValue != null ? "Update" : "Save");
            btnSaveRoom.setDisable(newValue == null);

            if (newValue != null) {
                txtRoomTypeId.setText(newValue.getRoomTypeId());
                txtType.setText(newValue.getType());
                txtKeyMoney.setText(String.valueOf(newValue.getKeyMoney()));
                txtQty.setText(String.valueOf(newValue.getQty()));

                txtRoomTypeId.setDisable(false);
                txtType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
            }
        });
        txtQty.setOnAction(event -> btnSaveRoom.fire());
        loadAllRooms();
    }

    private void loadAllRooms() {
        tblRoom.getItems().clear();
        /*Get all rooms*/
        try {
            ArrayList<RoomDTO> allRooms = roomBO.getAllRoomTypes();
            for (RoomDTO room : allRooms) {
                tblRoom.getItems().add(new RoomTM(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtRoomTypeId.setDisable(true);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);
        btnSaveRoom.setDisable(true);
        btnDeleteRoom.setDisable(true);
    }

    public void saveRoomOnAction(ActionEvent actionEvent) {
        String roomTypeId = txtRoomTypeId.getText();
        String type = txtType.getText();

        if (!type.matches("[A-Za-z0-9-/ ]+")) {
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
                //Save Room
                if (roomBO.add(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                }
                tblRoom.getItems().add(new RoomTM(roomTypeId, type, keyMoney, qty));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                /*Update Room*/
                if (roomBO.update(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
                }

                RoomTM selectedRoom = tblRoom.getSelectionModel().getSelectedItem();
                selectedRoom.setType(type);
                selectedRoom.setKeyMoney(keyMoney);
                selectedRoom.setQty(qty);
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
            if (roomBO.delete(roomTypeId)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted.! " + roomTypeId).show();
            }
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            tblRoom.getSelectionModel().clearSelection();
            initUI();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the room " + roomTypeId).show();
        }
    }

    public void addNewRoomOnAction(ActionEvent actionEvent) {
        txtRoomTypeId.setDisable(false);
        txtType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        txtRoomTypeId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtRoomTypeId.requestFocus();
        btnSaveRoom.setDisable(false);
        btnSaveRoom.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

}
