package carshop_service.application;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquidBaseInitialize {
    private static ConfigLoader configLoader;

    public static void main(String[] args) {
        configLoader = new ConfigLoader("application.properties");
        try {
            Connection connection = DriverManager.getConnection(configLoader.getProperty("liquibase.url"),
                    configLoader.getProperty("liquibase.username"),configLoader.getProperty("liquibase.password"));
            Database database =
                    DatabaseFactory
                            .getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase
                    = new Liquibase(configLoader.getProperty("liquibase.changelog"), new ClassLoaderResourceAccessor(),database);
            liquibase.update();
        }
        catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
