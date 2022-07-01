package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> roomList = new ArrayList<>();

    public Room(String roomTypeId, String type, double keyMoney, int qty) {
        this.roomTypeId = roomTypeId;
        this.type = type;
        this.keyMoney = keyMoney;
        this.qty = qty;
    }
}
