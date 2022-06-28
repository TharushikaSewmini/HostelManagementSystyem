package lk.ijse.hostelmanagementsystem.util;

import lk.ijse.hostelmanagementsystem.entity.Reservation;
import lk.ijse.hostelmanagementsystem.entity.Room;
import lk.ijse.hostelmanagementsystem.entity.Student;
import lk.ijse.hostelmanagementsystem.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        /*Configuration configuration = new Configuration().configure().addAnnotatedClass(Owner.class).addAnnotatedClass(Pet.class)
                .addAnnotatedClass(Customer.class);
        sessionFactory = configuration.buildSessionFactory();
    }*/

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().loadProperties("hibernate.properties").build();

        Metadata meta = new MetadataSources(ssr)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Reservation.class).getMetadataBuilder().build();

        sessionFactory =meta.getSessionFactoryBuilder().build();

    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration()
                : factoryConfiguration;
    }
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
