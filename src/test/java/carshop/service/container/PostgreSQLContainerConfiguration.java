package carshop.service.container;

import carshop.service.application.ConfigLoader;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Component
public class PostgreSQLContainerConfiguration {
        @Container
        private PostgreSQLContainer<?> postgreSQLContainer;
        private ConfigLoader configLoader;

        public PostgreSQLContainerConfiguration(){
            this.configLoader = new ConfigLoader("application.yml");
            String username = configLoader.getProperty("username");
            String password = configLoader.getProperty("password");
            this.postgreSQLContainer = new PostgreSQLContainer<>(configLoader.getProperty("version"))
                    .withDatabaseName(configLoader.getProperty("database"))
                    .withUsername(username)
                    .withPassword(password);
            postgreSQLContainer.start();
            String jdbcUrl = postgreSQLContainer.getJdbcUrl();
            try (Connection connection = DriverManager.getConnection(jdbcUrl,username,password)) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase(configLoader.getProperty("changelog"), new ClassLoaderResourceAccessor(), database);
                liquibase.update();
            } catch (SQLException | LiquibaseException e) {
                throw new RuntimeException(e);
            }
        }
}
