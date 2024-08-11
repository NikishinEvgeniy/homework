package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Client;
import carshop_service.entity.StandartClientBuilder;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
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

@Testcontainers
public class ClientDaoTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("car_service")
            .withUsername("evgen")
            .withPassword("admin");
    private static ClientDao clientDao;

    @BeforeAll
    static void setUp() throws SQLException, LiquibaseException {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        ConfigLoader configLoader = new ConfigLoader(jdbcUrl,password,username);
        clientDao = new ClientDaoImpl(configLoader);
        Database database = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(
                        new JdbcConnection(DriverManager.getConnection(jdbcUrl,username,password))
                );
        Liquibase liquibase = new Liquibase("database/changelog.xml",new ClassLoaderResourceAccessor(),database);
        liquibase.update();
    }

    @Test
    @DisplayName("Клиент достается из базы данных")
    void getClientTest(){
        int id = 2;
        Assertions.assertNotNull(clientDao.getClient(id));
    }

    @Test
    @DisplayName("Клиент добавляется в базу данных")
    void addClientTest(){
        String name = "test";
        Client client = new StandartClientBuilder()
                .name(name)
                .build();
        clientDao.addClient(client);
        Assertions.assertTrue(clientDao.getAllClients().stream().anyMatch(x -> x.getName().equals(name)));
    }

    @Test
    @DisplayName("Клиент обновляется в базе данных")
    void updateClientTest(){
        int id = 2;
        Client clientFromBd = clientDao.getClient(id);
        String name = "update";
        Client client = new StandartClientBuilder()
                .id(id)
                .name(name)
                .build();
        clientDao.updateClient(client);
        Assertions.assertNotEquals(clientFromBd,clientDao.getClient(id));
    }

    @Test
    @DisplayName("Клиент существует в базе данных ( поиск логину,паролю,роли )")
    void clientIsExistTest(){
        int id = 1;
        String login = "admin";
        String password = "admin";
        String role = "admin";
        Client client = new StandartClientBuilder()
                .login(login)
                .password(password)
                .role(role)
                .build();
        Assertions.assertTrue(clientDao.isExist(client));
    }

}
