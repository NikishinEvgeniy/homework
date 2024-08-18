package carshop_service.container;

import carshop_service.application.ConfigLoader;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Getter;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class PostgreContainer {

    @Container
    private PostgreSQLContainer<?> postgreSQLContainer;
    private ConfigLoader configLoader;

    public PostgreContainer(){
        this.configLoader = new ConfigLoader("application.properties");
        String username = configLoader.getProperty("liquidbase.username");
        String password = configLoader.getProperty("liquidbase.password");
        this.postgreSQLContainer = new PostgreSQLContainer<>(configLoader.getProperty("liquidbase.version"))
                .withDatabaseName(configLoader.getProperty("liquidbase.database"))
                .withUsername(username)
                .withPassword(password);
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        try (Connection connection = DriverManager.getConnection(jdbcUrl,username,password)) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(configLoader.getProperty("liquidbase.changelog"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}
