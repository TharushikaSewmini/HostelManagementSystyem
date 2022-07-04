package lk.ijse.hostelmanagementsystem.view.tm;

import lk.ijse.hostelmanagementsystem.entity.Reservation;
import lk.ijse.hostelmanagementsystem.entity.Room;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomTM {
    private String sId;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;
    private List<Reservation> studentList = new ArrayList<>();

    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;
    private List<Reservation> roomList = new ArrayList<>();

    private String resId;
    private LocalDate date;

    private Student student;

    private Room room;

    private String status;

    public CustomTM(String sId, String name, String resId, String status) {
        this.sId = sId;
        this.name = name;
        this.resId = resId;
        this.status = status;
    }
}
