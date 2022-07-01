package lk.ijse.hostelmanagementsystem.dao.custom.impl;

import lk.ijse.hostelmanagementsystem.dao.custom.ReservationDAO;
import lk.ijse.hostelmanagementsystem.entity.Reservation;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public boolean add(Reservation entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Reservation entity) throws Exception {
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

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(session.get(Reservation.class, id));
            transaction.commit();
            session.close();
            return true;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return false;
    }

    @Override
    public Reservation get(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Reservation reservation = session.get(Reservation.class, id);
            transaction.commit();
            session.close();
            return reservation;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }

    @Override
    public List<Reservation> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("FROM Reservation");
            List<Reservation> roomList = query.list();

            transaction.commit();
            session.close();

            return roomList;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }

    /*@Override
    public boolean exist(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Room room = session.get(Room.class, s);
        //String id = (String) sqlQuery.uniqueResult();

        transaction.commit();
        session.close();
        return true;
    }*/

    @Override
    public String generateNewID() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery sqlQuery = session.createSQLQuery("SELECT resId FROM Reservation ORDER BY resId DESC LIMIT 1");
        String id = (String) sqlQuery.uniqueResult();

        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Reservation search(String type) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("FROM Reservation WHERE resId= : room_type");
            query.setParameter("room_type", type);
            Reservation roomType = (Reservation) query.uniqueResult();
            transaction.commit();
            session.close();
            return roomType;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;

    }
}
