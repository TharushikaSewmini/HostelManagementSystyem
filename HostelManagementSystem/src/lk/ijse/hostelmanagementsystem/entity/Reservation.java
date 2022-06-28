package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Reservation {
    @Id
    private String resId;
    private LocalDate date;

    //private Student sId;

    //private Room roomTypeId;

    private String status;
}
