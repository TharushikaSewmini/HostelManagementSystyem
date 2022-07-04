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

        NativeQuery sqlQuery = session.createSQLQuery("SELECT s.sId,s.name, re.resId, re.status FROM Student s INNER JOIN Reservation re ON s.sId = re.student_sId WHERE re.status NOT LIKE 'Paid%' ORDER BY s.sId");
        sqlQuery.addEntity(CustomEntity.class);
        List<CustomEntity> students = sqlQuery.list();

        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public boolean update(CustomEntity entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(entity);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return false;
    }
}
