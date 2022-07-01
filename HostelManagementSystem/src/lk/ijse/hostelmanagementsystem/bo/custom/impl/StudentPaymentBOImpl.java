package lk.ijse.hostelmanagementsystem.bo.custom.impl;

import lk.ijse.hostelmanagementsystem.bo.custom.StudentBO;
import lk.ijse.hostelmanagementsystem.bo.custom.StudentPaymentBO;
import lk.ijse.hostelmanagementsystem.dao.DAOFactory;
import lk.ijse.hostelmanagementsystem.dao.DAOType;
import lk.ijse.hostelmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.hostelmanagementsystem.dao.custom.RoomDAO;
import lk.ijse.hostelmanagementsystem.dto.CustomDTO;
import lk.ijse.hostelmanagementsystem.dto.RoomDTO;
import lk.ijse.hostelmanagementsystem.dto.StudentDTO;
import lk.ijse.hostelmanagementsystem.entity.CustomEntity;
import lk.ijse.hostelmanagementsystem.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class StudentPaymentBOImpl implements StudentPaymentBO {
    private final QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> getStudentNotPaidKeyMoney() throws Exception {
        List<CustomEntity> all =  queryDAO.getStudentNotPaidKeyMoney();
        ArrayList<CustomDTO> list = new ArrayList<>();
        for (CustomEntity studentList : all) {
            list.add(new CustomDTO(
                    studentList.getSId(),
                    studentList.getName(),
                    studentList.getResId(),
                    studentList.getStatus()));
        }
        return list;
    }

}
