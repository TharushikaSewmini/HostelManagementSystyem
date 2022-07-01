package lk.ijse.hostelmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class User implements SuperEntity {
    @Id
    private String userId;
    private String userName;
    private String password;
}
