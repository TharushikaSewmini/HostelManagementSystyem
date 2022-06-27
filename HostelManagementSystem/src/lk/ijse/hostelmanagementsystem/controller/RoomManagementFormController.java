package lk.ijse.hostelmanagementsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.view.tm.RoomTM;

import java.sql.SQLException;
import java.util.ArrayList;

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
    public JFXButton btnDeleteItem;
    public JFXButton btnAddNewRoom;
    public JFXButton btnDeleteRoom;

    public void initialize() {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("description"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        //colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDeleteItem.setDisable(newValue == null);
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
        try {
            ArrayList<RoomDTO> allRooms = itemBO.getAllItems();
            for (RoomDTO room : allRooms) {
                tblRoom.getItems().add(new RoomTM(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtRoomId.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        //txtQtyOnHand.clear();
        txtRoomId.setDisable(true);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtRoomId.setEditable(false);
        btnSaveItem.setDisable(true);
        btnDeleteItem.setDisable(true);
    }

    public void saveRoomOnAction(ActionEvent actionEvent) {
    }

    public void addNewRoomOnAction(ActionEvent actionEvent) {
    }

    public void deleteRoomOnAction(ActionEvent actionEvent) {
    }
}
