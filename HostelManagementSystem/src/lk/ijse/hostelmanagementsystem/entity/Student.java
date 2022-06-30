package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Student {
    @Id
    private String sId;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

    /*@OneToMany(mappedBy = "student")
    private List<Reservation> studentList = new ArrayList<>();*/
}
