package lk.ijse.hostelmanagementsystem.dao.custom.impl;

import lk.ijse.hostelmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.hostelmanagementsystem.entity.CustomEntity;
import lk.ijse.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<CustomEntity> getStudentNotPaidKeyMoney() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();


        NativeQuery sqlQuery = session.createSQLQuery("SELECT s.sId,s.name, s.address,s.contact,s.dob,s.gender, re.resId, re.status FROM Reservation re INNER JOIN Student s ON re.student = s.sId WHERE re.status NOT LIKE '%Paid%'");

        //SELECT s.sId,s.name, s.address,s.contact,s.dob,s.gender, re.resId, re.status FROM ((Reservation INNER JOIN Room r ON Reservation.room = r.roomTypeId) INNER JOIN Student s ON r.student = s.sId) WHERE re.pay

        /*SELECT SUM(qty) AS qty, OrderDetail.productCode, p.description FROM ((OrderDetail INNER JOIN Product p ON OrderDetail.productCode = p.code) INNER JOIN `Order` o ON OrderDetail.orderId = o.id)
        WHERE o.date BETWEEN '2022-1-01' AND '2022-3-31'
        GROUP BY(productCode)
                ORDER BY productCode ASC*/


        //sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //sqlQuery.setParameter(1, code);
        //List students = sqlQuery.list();
        sqlQuery.addEntity(CustomEntity.class);
        List<CustomEntity> studentList= sqlQuery.getResultList();

        transaction.commit();

        return studentList;

        //return list;
    }
}
