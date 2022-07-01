package lk.ijse.hostelmanagementsystem.dao.custom;

import lk.ijse.hostelmanagementsystem.dao.SuperDAO;
import lk.ijse.hostelmanagementsystem.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<CustomEntity> getStudentNotPaidKeyMoney() throws Exception;
}
