package carshop_service.application;

import carshop_service.constant.SqlQuery;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.*;

/**
 * Класс, предназначенный для миграции базы данных.
 */
public class LiquidBaseInitializer {
    private static ConfigLoader configLoader;

    public static void main(String[] args) {
        configLoader = new ConfigLoader("application.properties");
        try {
            Connection connection = DriverManager.getConnection(configLoader.getProperty("liquidbase.url"),
                    configLoader.getProperty("liquidbase.username"),configLoader.getProperty("liquidbase.password"));
            Database database =
                    DatabaseFactory
                            .getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase
                    = new Liquibase(configLoader.getProperty("liquidbase.changelog"), new ClassLoaderResourceAccessor(),database);
            liquibase.update();
        }
        catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
