package carshop_service.dao;

import carshop_service.entity.Log;
<<<<<<< Updated upstream

import java.util.HashMap;
import java.util.List;

=======
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных log
 */
@AllArgsConstructor
>>>>>>> Stashed changes
public class LogDaoImpl implements LogDao {
    private HashMap<Integer, Log> logs;

<<<<<<< Updated upstream
    public LogDaoImpl(){
        this.logs = new HashMap<>();
=======
    private final DataBaseConfiguration database;


    public void addLog(Log log){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.ADD_LOG_QUERY);
            if(log.getDateTime() != null) statement.setString(1,log.getDateTime().toString());
            else statement.setString(1,null);
            statement.setInt(2,log.getClientId());
            if(log.getAction() != null) statement.setString(3,log.getAction().toString());
            else statement.setString(3,null);
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
>>>>>>> Stashed changes
    }

    public void addLog(Log log){
        this.logs.put(log.getId(),log);
    }

    @Override
    public List<Log> getAllLogs() {
        return this.logs.values().stream().toList();
    }

}
