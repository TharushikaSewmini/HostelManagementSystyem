package lk.ijse.hostelmanagementsystem.dao.custom.impl;

import lk.ijse.hostelmanagementsystem.dao.custom.UserDAO;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.entity.User;
import lk.ijse.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
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
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public User get(String s) throws Exception {
        return null;
    }

    @Override
    public List<User> getAll() throws Exception {
        return null;
    }

    @Override
    public String generateNewID() throws Exception {
        return null;
    }

    @Override
    public User search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("FROM User WHERE userId= : user_Id");
            query.setParameter("user_Id", id);
            User user = (User) query.uniqueResult();
            transaction.commit();
            session.close();
            return user;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }

    @Override
    public String getUserName(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("SELECT userName FROM User WHERE userId= : user_id");
            query.setParameter("user_id", id);
            String user = (String) query.uniqueResult();
            transaction.commit();
            session.close();
            return user;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }
}
