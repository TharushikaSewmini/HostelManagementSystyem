package lk.ijse.hostelmanagementsystem.bo.custom;

import lk.ijse.hostelmanagementsystem.bo.SuperBO;
import lk.ijse.hostelmanagementsystem.dto.CustomDTO;

import java.util.ArrayList;

public interface StudentPaymentBO extends SuperBO {
    ArrayList<CustomDTO> getStudentNotPaidKeyMoney() throws Exception;
}
