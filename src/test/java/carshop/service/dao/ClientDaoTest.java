package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.constant.ClientRole;
import carshop.service.constant.ClientState;
import carshop.service.container.PostgreContainer;
import carshop.service.entity.Client;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

public class ClientDaoTest {

    private static PostgreSQLContainer<?> postgreContainer;
    private static ClientDao clientDao;

    @BeforeAll
    public static void setUp() {
        postgreContainer = new PostgreContainer().getPostgreSQLContainer();
        String password = postgreContainer.getPassword();
        String username = postgreContainer.getUsername();
        DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration(postgreContainer.getJdbcUrl()
                ,username,password);
        clientDao = new ClientDaoImpl(dataBaseConfiguration);
    }

    @AfterAll
    public static void closeConnection(){
        postgreContainer.close();
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
        Client client = Client.builder()
                .state(ClientState.CLIENT_FILTER_STATE)
                .role(ClientRole.ADMIN)
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
        Client client = Client.builder()
                .id(id)
                .role(ClientRole.CLIENT)
                .state(ClientState.CLIENT_FILTER_STATE)
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
        Client client = Client.builder()
                .login(login)
                .password(password)
                .role(ClientRole.ADMIN)
                .build();
        Assertions.assertTrue(clientDao.isExist(client));
    }

}
