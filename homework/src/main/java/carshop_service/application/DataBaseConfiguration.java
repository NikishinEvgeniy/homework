package carshop_service.application;

import carshop_service.constant.SqlQuery;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class DataBaseConfiguration {

    private final String url;
    private final String username;
    private final String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void setSchema(Statement statement){
        try {
            statement.execute(SqlQuery.SET_SCHEMA_TO_PRIVATE);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
