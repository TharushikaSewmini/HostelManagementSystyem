package lk.ijse.hostelmanagementsystem.dao;


import lk.ijse.hostelmanagementsystem.dao.custom.impl.RoomDAOImpl;

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
            default:
                return null;
        }
    }
}
