package lk.ijse.hostelmanagementsystem.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostelmanagementsystem.bo.BOFactory;
import lk.ijse.hostelmanagementsystem.bo.BOType;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentPaymentBO;
import lk.ijse.hostelmanagementsystem.dto.CustomDTO;
import lk.ijse.hostelmanagementsystem.view.tm.CustomTM;

import java.util.ArrayList;

public class StudentPaymentFormController {
    public TableView<CustomTM> tblStudentPayment;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colReservationId;
    public TableColumn colStatus;

    // Property Injection(DI)
    private final StudentPaymentBO studentPaymentBO = BOFactory.getInstance().getBO(BOType.PAYMENT);

    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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
}
