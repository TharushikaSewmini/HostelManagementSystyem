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

@Entity
public class Student implements SuperEntity {
    @Id
    private String sId;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> studentList = new ArrayList<>();

    public Student(String sId, String name, String address, String contact, LocalDate dob, String gender) {
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }
}
