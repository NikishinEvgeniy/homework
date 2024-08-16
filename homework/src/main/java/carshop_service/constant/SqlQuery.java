package carshop_service.constant;

/**
 * Класс, хранящий SQL запросы
 */
public class SqlQuery {

    public static final String ADD_CAR_QUERY =
                    " INSERT INTO car (brand,model,price,year_of_release,condition) VALUES(?,?,?,?,?) ";
    public static final String ADD_CLIENT_QUERY =
            " INSERT INTO client (name,surname,login,password,role,state,count_of_buy) VALUES(?,?,?,?,?,?,?) ";
    public static final String ADD_LOG_QUERY =
            " INSERT INTO log (date_time,client_id,action) VALUES(?,?,?) ";
    public static final String ADD_ORDER_QUERY =
            " INSERT INTO orders (client_id,car_id,state,type,date_time) VALUES(?,?,?,?,?) ";


    public static final String UPDATE_CAR_QUERY =
                    " UPDATE car " +
                    " SET brand = ?, model = ?, " +
                    " price = ?, year_of_release = ?, " +
                    " condition = ? " +
                    " WHERE id = ? ";
    public static final String UPDATE_CLIENT_QUERY =
            " UPDATE client " +
                    " SET name = ?,surname = ?, " +
                    " login = ?, password = ?, " +
                    " role = ?, state = ?, " +
                    " count_of_buy = ? " +
                    " Where id = ? ";
    public static final String UPDATE_ORDER_QUERY =
            "UPDATE orders " +
                    " SET client_id = ?, car_id = ?," +
                    " state = ?, type = ?, " +
                    " date_time = ? " +
                    " WHERE id = ?";

    public static final String DELETE_CAR_QUERY = " DELETE FROM car WHERE id = ? ";
    public static final String DELETE_ORDER_QUERY = " DELETE FROM orders WHERE id = ? ";

    public static final String GET_CAR_QUERY = " SELECT * FROM car WHERE id=? ";
    public static final String GET_CLIENT_QUERY = "SELECT * FROM client Where id =? ";
    public static final String GET_ORDER_QUERY = " SELECT * FROM orders WHERE id = ? ";

    public static final String GET_CARS_QUERY = " SELECT * FROM car ";
    public static final String GET_ORDERS_QUERY = " SELECT * FROM orders";
    public static final String GET_LOGS_QUERY = " SELECT * FROM log ";
    public static final String GET_CLIENTS_QUERY = " SELECT * FROM client ";

    public static final String CLIENT_IS_EXIST_BY_PASSWORD_LOGIN_ROLE_QUERY =
                    " SELECT * FROM client Where login =? and password =? and role =? ";

    public static final String CREATE_SERVICE_SCHEME = "CREATE SCHEMA ? AUTHORIZATION ?";
    public static final String DELETE_ACCESS_TO_ALL_USER = "REVOKE ALL ON SCHEMA ? FROM public";
    public static final String GET_ACCESS_TO_USER = "GRANT ALL ON SCHEMA ? TO ?";
    public static final String SET_SCHEMA_TO_PRIVATE = "SET search_path TO private_schema;";



}
