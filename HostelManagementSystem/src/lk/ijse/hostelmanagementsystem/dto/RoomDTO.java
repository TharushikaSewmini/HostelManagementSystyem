package lk.ijse.hostelmanagementsystem.dto;

import javafx.scene.control.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {
    private String roomTypeId;
    private String type;
    private double keyMoney;
    private int qty;

    public RoomDTO(Label lblReservationId, String roomTypeId, String type, double keyMoney) {
    }

    public RoomDTO(String roomTypeId, String type, double keyMoney) {
        this.roomTypeId = roomTypeId;
        this.type = type;
        this.keyMoney = keyMoney;
    }
}
