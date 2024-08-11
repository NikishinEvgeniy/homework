package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.constant.LogAction;
import carshop_service.entity.Log;
import carshop_service.entity.StandartLogBuilder;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Testcontainers
public class LogDaoTest {
    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>()
            .withDatabaseName("postgres:latest")
            .withPassword("admin")
            .withUsername("evgen");
    private static LogDao logDao;

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        ConfigLoader configLoader = new ConfigLoader(jdbcUrl,password,username);
        logDao = new LogDaoImpl(configLoader);
        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(DriverManager.getConnection(jdbcUrl,username,password)));
        Liquibase liquibase = new Liquibase("database/changelog.xml",new ClassLoaderResourceAccessor(),database);
        liquibase.update();
    }

    @Test
    @DisplayName("Лог добавляется в базу данных")
    void addLogTest(){
        LocalDateTime dateTime = LocalDateTime.now();
        Log  log = new StandartLogBuilder()
                .action(LogAction.ADD_CLIENT_ACTION)
                .dateTime(dateTime)
                .build();
        logDao.addLog(log);
        Assertions.assertTrue(logDao.getAllLogs().stream().anyMatch(x -> x.getAction().equals(LogAction.ADD_CLIENT_ACTION)));
    }

    @Test
    @DisplayName("Список логов достается из бд")
    void getAllLogs(){
        List<Log> logs = logDao.getAllLogs();
        Assertions.assertFalse(logs.isEmpty());
    }
}
