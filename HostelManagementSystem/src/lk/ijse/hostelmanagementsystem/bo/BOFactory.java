package lk.ijse.hostelmanagementsystem.bo;

import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.StudentBOImpl;
import lk.ijse.hostelmanagementsystem.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return (null == boFactory) ? boFactory = new BOFactory() : boFactory;
    }

    public <T extends SuperBO> T getBO(BOType boType){
        switch (boType){
            case ROOM:
                return (T) new RoomBoImpl();

            case STUDENT:
                return (T) new StudentBOImpl();

            case REGISTRATION:
                return (T) new RegistrationBOImpl();

            case USER:
                return (T) new UserBOImpl();

            default:
                return null;
        }
    }
}
