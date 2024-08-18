package carshop_service.dao;

import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.LogAction;
import carshop_service.constant.SqlQuery;
import carshop_service.entity.Log;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных log
 */

@AllArgsConstructor
public class LogDaoImpl implements LogDao {

    private final DataBaseConfiguration database;

    public void addLog(Log log){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.ADD_LOG_QUERY);
            statement.setString(1,log.getDateTime().toString());
            statement.setInt(2,log.getClientId());
            statement.setString(3,log.getAction().toString());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @Override
    public List<Log> getAllLogs() {
        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        List<Log> logs = new LinkedList<>();
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SqlQuery.GET_LOGS_QUERY);
            while (resultSet.next()){
                logs.add(
                        Log.builder()
                        .action(LogAction.valueOf(resultSet.getString("action")))
                        .clientId(resultSet.getInt("client_id"))
                        .id(resultSet.getInt("id"))
                        .dateTime(LocalDateTime.parse(resultSet.getString("date_time")))
                        .build()
                        );
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return logs;
    }

}
