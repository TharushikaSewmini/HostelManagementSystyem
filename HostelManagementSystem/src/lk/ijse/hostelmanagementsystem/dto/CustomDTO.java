package lk.ijse.hostelmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomDTO {
    private String sId;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;

    private String resId;
    private LocalDate date;
    private String status;

    public CustomDTO(String sId, String name, String resId, String status) {
        this.sId = sId;
        this.name = name;
        this.resId = resId;
        this.status = status;
    }
}
