package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Log;
import carshop_service.entity.StandartLogBuilder;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class LogDaoImpl implements LogDao {

    private ConfigLoader configLoader;

    public void addLog(Log log){
        Connection connection = configLoader.getConnection();
        String addLogQuery = "INSERT INTO log (date_time,client_id,action) VALUES(?,?,?)";
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            statement = connection.prepareStatement(addLogQuery);
            statement.setString(1,log.getDateTime().toString());
            statement.setInt(2,log.getClientId());
            statement.setString(3,log.getAction());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
                if(statement!=null) statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<Log> getAllLogs() {
        Connection connection = configLoader.getConnection();
        Statement statement = null;
        List<Log> logs = new LinkedList<>();
        try {
            configLoader.setSchema(connection.createStatement());
            String getAllLogsQuery = "SELECT * FROM log";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllLogsQuery);
            while (resultSet.next()){
                logs.add(
                        new StandartLogBuilder()
                        .action(resultSet.getString("action"))
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
                connection.close();
                if(statement != null) statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return logs;
    }

}
