package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Reservation implements SuperEntity {
    @Id
    private String resId;
    @CreationTimestamp
    private LocalDate date;

    @ManyToOne
    private Student student;

    @ManyToMany(mappedBy = "roomList")
    private List<Room> room;

    private String status;

    public Reservation(String resId, String status) {
        this.resId = resId;
        this.status = status;
    }

    public Reservation(String resId, LocalDate date, String status) {
        this.resId = resId;
        this.date = date;
        this.status = status;
    }

    /*public Reservation(String resId, String status) {
    }*/
}
