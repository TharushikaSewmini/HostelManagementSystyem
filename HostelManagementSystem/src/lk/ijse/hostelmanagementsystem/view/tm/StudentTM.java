package lk.ijse.hostelmanagementsystem.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTM {
    private String sId;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;
}
