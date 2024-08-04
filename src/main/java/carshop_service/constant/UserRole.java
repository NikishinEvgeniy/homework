package carshop_service.constant;

public class UserRole {
    public static final String CLIENT = "client";
    public static final String MANAGER = "manager";
    public static final String ADMIN = "admin";

    public static String[] getAllRoles(){
        return new String[]{CLIENT,MANAGER,ADMIN};
    }
}
