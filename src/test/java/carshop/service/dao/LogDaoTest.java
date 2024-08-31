package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.container.PostgreSQLContainerConfiguration;
import carshop.service.entity.Log;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import ya.lab.loggable_starter.constant.LogAction;

import java.time.LocalDateTime;
import java.util.List;


public class LogDaoTest {

    private static PostgreSQLContainer<?> postgreContainer;
    private static LogDao logDao;

    @BeforeAll
    public static void setUp() {
        postgreContainer = new PostgreSQLContainerConfiguration().getPostgreSQLContainer();
        String password = postgreContainer.getPassword();
        String username = postgreContainer.getUsername();
        DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration(postgreContainer.getJdbcUrl()
                ,username,password);
        logDao = new LogDaoImpl(dataBaseConfiguration);
    }


    @AfterAll
    public static void closeConnection(){
        postgreContainer.close();
    }

    @Test
    @DisplayName("Лог добавляется в базу данных")
    void addLogTest(){
        LocalDateTime dateTime = LocalDateTime.now();
        Log  log = Log.builder()
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
