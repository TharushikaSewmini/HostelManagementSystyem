package lk.ijse.hostelmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {
    private String resId;
    private LocalDate date;
    private StudentDTO studentDTO;
    private List<RoomDTO> roomList;
    private String status;

    public ReservationDTO(String resId, LocalDate date, String status) {
        this.resId = resId;
        this.date = date;
        this.status = status;
    }
}
