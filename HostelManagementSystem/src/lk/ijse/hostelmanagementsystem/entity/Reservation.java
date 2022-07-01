package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Reservation implements SuperEntity {
    @Id
    private String resId;
    private LocalDate date;

    @ManyToOne
    private Student student;

    @ManyToMany
    private List<Room> room;

    private String status;

    public Reservation(String resId, LocalDate date, String status) {
        this.resId = resId;
        this.date = date;
        this.status = status;
    }
}
