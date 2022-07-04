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
import lk.ijse.hostelmanagementsystem.bo.custom.RegistrationBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentPaymentBO;
import lk.ijse.hostelmanagementsystem.dto.CustomDTO;
import lk.ijse.hostelmanagementsystem.dto.ReservationDTO;
import lk.ijse.hostelmanagementsystem.view.tm.CustomTM;

import java.util.ArrayList;

public class StudentPaymentFormController {
    public TableView<CustomTM> tblStudentPayment;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colReservationId;
    public TableColumn colStatus;
    public JFXTextField txtSId;
    public JFXTextField txtName;
    public JFXTextField txtPay;
    public JFXTextField txtReservationId;
    public JFXButton btnSaveStudent;
    public JFXTextField txtStatus;

    // Property Injection(DI)
    private final StudentPaymentBO studentPaymentBO = BOFactory.getInstance().getBO(BOType.PAYMENT);
    private final RegistrationBO registrationBO = BOFactory.getInstance().getBO(BOType.REGISTRATION);

    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblStudentPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSaveStudent.setDisable(newValue == null);

            if (newValue != null) {
                txtSId.setText(newValue.getSId());
                txtName.setText(newValue.getName());
                txtReservationId.setText(newValue.getResId());
                txtStatus.setText(newValue.getStatus());
            }
        });

        initUI();
        loadStudentNotPaidKeyMoney();
    }

    private void loadStudentNotPaidKeyMoney() {
        tblStudentPayment.getItems().clear();
        /*Get all students*/
        try {
            ArrayList<CustomDTO> studentList = studentPaymentBO.getStudentNotPaidKeyMoney();
            for (CustomDTO list : studentList) {
                tblStudentPayment.getItems().add(new CustomTM(list.getSId(), list.getName(), list.getResId(), list.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtSId.clear();
        txtName.clear();
        txtReservationId.clear();
        txtStatus.clear();
        txtSId.setEditable(false);
        txtName.setEditable(false);
        txtReservationId.setEditable(false);
        txtStatus.setEditable(false);
        btnSaveStudent.setDisable(true);
        tblStudentPayment.refresh();
    }

    public void saveStudentOnAction(ActionEvent actionEvent) {
        String studentId = txtSId.getText();
        String name = txtName.getText();
        String resId = txtReservationId.getText();
        String status = txtStatus.getText();

        double doubleStatus = Double.parseDouble(status.replace(" has to be paid", ""));
        System.out.println(doubleStatus);

        double payment = Double.parseDouble(txtPay.getText());
        double newRemainKeyMoney = doubleStatus-payment;
        String newStatus = (newRemainKeyMoney == 0) ? "Paid" : newRemainKeyMoney + " has to be paid";

        try {
            if(registrationBO.updateReservationStatus(new ReservationDTO(resId, newStatus))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Status updated.!").show();
            }

            CustomTM selectedStudent = tblStudentPayment.getSelectionModel().getSelectedItem();
            selectedStudent.setSId(studentId);
            selectedStudent.setName(name);
            selectedStudent.setResId(resId);
            selectedStudent.setStatus(newStatus);
            tblStudentPayment.getItems().remove(tblStudentPayment.getSelectionModel().getSelectedItem());
            tblStudentPayment.getSelectionModel().clearSelection();

            clearUI();
            /*tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            tblStudent.getSelectionModel().clearSelection();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearUI() {
        txtSId.clear();
        txtName.clear();
        txtReservationId.clear();
        txtStatus.clear();
        txtPay.clear();
        txtSId.setEditable(false);
        txtName.setEditable(false);
        txtReservationId.setEditable(false);
        txtStatus.setEditable(false);
        txtPay.requestFocus();
        btnSaveStudent.setDisable(true);
        tblStudentPayment.refresh();
    }
}
