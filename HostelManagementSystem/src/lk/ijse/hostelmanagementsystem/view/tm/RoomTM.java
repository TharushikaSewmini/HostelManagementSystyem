package lk.ijse.hostelmanagementsystem.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomTM {
    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;

    public RoomTM(String roomTypeId, String type, double keyMoney) {
        this.roomTypeId = roomTypeId;
        this.type = type;
        this.keyMoney = keyMoney;
    }
}
