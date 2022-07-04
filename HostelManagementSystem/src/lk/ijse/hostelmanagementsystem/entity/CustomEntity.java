package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity(name = "custom")
public class CustomEntity implements SuperEntity {
    @Id
    private String sId;
    private String name;
    /*@Column(columnDefinition = "TEXT")
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> studentList = new ArrayList<>();

    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;
    @ManyToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> roomList = new ArrayList<>();*/

    private String resId;
    /*private LocalDate date;

    @ManyToOne
    private Student student;

    @ManyToMany
    private List<Room> room;*/

    private String status;


}
