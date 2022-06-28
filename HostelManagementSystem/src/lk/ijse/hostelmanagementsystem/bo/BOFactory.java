package lk.ijse.hostelmanagementsystem.bo;

import lk.ijse.hostelmanagementsystem.bo.custom.impl.RoomBoImpl;

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
            default:
                return null;
        }
    }
}
