package lk.ijse.hostelmanagementsystem.dao.custom.impl;

import lk.ijse.hostelmanagementsystem.dao.custom.StudentDAO;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean add(Student entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws Exception {
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
            session.delete(session.get(Student.class, id));
            transaction.commit();
            session.close();
            return true;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return false;
    }

    @Override
    public Student find(String s) throws Exception {
        return null;
    }

    @Override
    public List<Student> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("FROM Student");
            List<Student> studentList = query.list();

            transaction.commit();
            session.close();

            return studentList;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }

    @Override
    public String generateNewID() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery sqlQuery = session.createSQLQuery("SELECT sId FROM Student ORDER BY sId DESC LIMIT 1");
        String id = (String) sqlQuery.uniqueResult();

        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Student search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Query query = session.createQuery("FROM Student WHERE sId= : student_Id");
            query.setParameter("student_Id", id);
            Student student1 = (Student) query.uniqueResult();
            transaction.commit();
            session.close();
            return student1;
        } catch (Exception exception) {
            transaction.rollback();
        }
        return null;
    }
}
