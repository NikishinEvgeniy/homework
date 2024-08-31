package carshop.service.configuration;

import carshop.service.application.ConfigLoader;
import carshop.service.application.DataBaseConfiguration;
import carshop.service.dao.*;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@TestConfiguration
public class ApplicationDataBaseTestConfiguration {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer(){
        System.out.println("Создаюсь");
        ConfigLoader configLoader = new ConfigLoader("application.yml");
        String username = configLoader.getProperty("username");
        String password = configLoader.getProperty("password");
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(configLoader.getProperty("version"))
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
        System.out.println("Создался");
        return postgreSQLContainer;
    }

//    @Bean(name = "dataBaseTestBean")
//    public DataBaseConfiguration dataBaseConfiguration(){
//        PostgreSQLContainer<?> postgreSQLContainer = postgreSQLContainer();
//        return new DataBaseConfiguration(postgreSQLContainer.getJdbcUrl(),postgreSQLContainer.getUsername(),postgreSQLContainer.getPassword());
//    }
//
//    @Bean(name = "carDaoTestBean")
//    public CarDao carDaoBean(){
//        return new CarDaoImpl(dataBaseConfiguration());
//    }
}
