package lk.ijse.hostelmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private String sId;
    private String name;
    private String address;
    private String contact;
    private LocalDate date;
    private String gender;
}
