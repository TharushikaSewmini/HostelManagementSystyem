package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.view.tm.RoomTM;

import java.util.ArrayList;

public class RoomManagementFormController {
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
    public JFXRadioButton rbtnRoomTypeId1;
    public JFXRadioButton rbtnRoomTypeId2;
    public JFXRadioButton rbtnRoomTypeId3;
    public JFXRadioButton rbtnRoomTypeId4;
    public ToggleGroup roomTypeId;

    // Property Injection(DI)
    private final RoomBoImpl roomBOImpl = BOFactory.getInstance().getBO(BOType.ROOM);

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
                txtType.setText(newValue.getType());
                txtKeyMoney.setText(String.valueOf(newValue.getKeyMoney()));
                txtQty.setText(String.valueOf(newValue.getQty()));

                rbtnRoomTypeId1.setSelected(true);
                rbtnRoomTypeId2.setSelected(true);
                rbtnRoomTypeId3.setSelected(true);
                rbtnRoomTypeId4.setSelected(true);
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
            ArrayList<RoomDTO> allRooms = roomBOImpl.getAllRoomTypes();
            for (RoomDTO room : allRooms) {
                tblRoom.getItems().add(new RoomTM(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        rbtnRoomTypeId1.setSelected(false);
        rbtnRoomTypeId2.setSelected(false);
        rbtnRoomTypeId3.setSelected(false);
        rbtnRoomTypeId4.setSelected(false);
        txtType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);
        btnSaveRoom.setDisable(true);
        btnDeleteRoom.setDisable(true);
    }

    private String getRoomTypeId() {
        if (rbtnRoomTypeId1.isSelected()) {
            return "RM-1324";
        } else if (rbtnRoomTypeId2.isSelected()) {
            return "RM-5467";
        } else if (rbtnRoomTypeId3.isSelected()) {
            return "RM-7896";
        } else if (rbtnRoomTypeId4.isSelected()) {
            return "RM-0093";
        }
        return null;

    }

    private String getType() {
        if (rbtnRoomTypeId1.isSelected()) {
            return "Non-AC";
        } else if (rbtnRoomTypeId2.isSelected()) {
            return "Non-AC/Food";
        } else if (rbtnRoomTypeId3.isSelected()) {
            return "AC";
        } else if (rbtnRoomTypeId4.isSelected()) {
            return "AC/Food";
        }
        return null;

    }

    public void saveRoomOnAction(ActionEvent actionEvent) {
        String roomTypeId = getRoomTypeId();
        String type = txtType.getText();

        /*if (!type.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid type").show();
            txtType.requestFocus();
            return;
        } else*/ if (!txtKeyMoney.getText().matches("^[0-9]+[.]?[0-9]*$")) {
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
                //Save Customer
                if (roomBOImpl.add(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved.!").show();
                }

                tblRoom.getItems().add(new RoomTM(roomTypeId, type, keyMoney, qty));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                /*Update Customer*/

                if (roomBOImpl.update(new RoomDTO(roomTypeId, type, keyMoney, qty))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated.!").show();
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
        rbtnRoomTypeId1.setSelected(false);
        rbtnRoomTypeId2.setSelected(false);
        rbtnRoomTypeId3.setSelected(false);
        rbtnRoomTypeId4.setSelected(false);
        txtType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtType.requestFocus();
        btnSaveRoom.setDisable(false);
        btnSaveRoom.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    public void roomTypeSetOnAction(ActionEvent actionEvent) {
        txtType.setText(getType());

    }

    /*private String generateNewId() {
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
        return s;
    }

    private boolean existRoom(String roomTypeTd) throws SQLException, ClassNotFoundException {
        //return roomBO.roomExist(roomTypeTd);
    }*/

}
