package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Room implements SuperEntity {
    @Id
    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;

    /*@OneToMany(mappedBy = "room")
    private List<Reservation> roomList = new ArrayList<>();*/
}
