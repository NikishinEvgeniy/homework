package carshop_service.constant;

public class OrderType {
    public static final String SALE = "SALE";
    public static final String PROCESSING = "PROCESSING";

    public static String[] getAllStates(){
        return new String[]{SALE,PROCESSING};
    }
}
