package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class CustomEntity implements SuperEntity {
    @Id
    private String sId;
    private String name;
    @Column(columnDefinition = "TEXT")
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

    public CustomEntity(String sId, String name, String resId, String status) {
        this.sId = sId;
        this.name = name;
        this.resId = resId;
        this.status = status;
    }
}
