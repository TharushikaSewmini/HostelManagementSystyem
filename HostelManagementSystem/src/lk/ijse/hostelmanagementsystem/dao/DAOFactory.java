package lk.ijse.hostelmanagementsystem.dao;

import lk.ijse.hostelmanagementsystem.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return (null == daoFactory) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public <T extends SuperDAO>T getDAO(DAOType daoType){
        switch (daoType){
            case ROOM:
                return (T) new RoomDAOImpl();

            case STUDENT:
                return (T) new StudentDAOImpl();

            case RESERVATION:
                return (T) new ReservationDAOImpl();

            case USER:
                return (T) new UserDAOImpl();

            case QUERYDAO:
                return (T) new QueryDAOImpl();

            default:
                return null;
        }
    }
}
