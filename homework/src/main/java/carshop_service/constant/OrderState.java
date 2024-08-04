package carshop_service.constant;

public class OrderState {
    public static final String OK = "OK";
    public static final String UPDATE = "UPDATE";


    public static String[] getAllStates(){
        return new String[]{OK,UPDATE};
    }
}
