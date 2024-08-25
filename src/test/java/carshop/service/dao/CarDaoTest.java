package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.container.PostgreContainer;
import carshop.service.entity.Car;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;


public class CarDaoTest {

    private static PostgreSQLContainer<?> postgreContainer;
    private static CarDao carDao;

    @BeforeAll
    public static void setUp() {
        postgreContainer = new PostgreContainer().getPostgreSQLContainer();
        String password = postgreContainer.getPassword();
        String username = postgreContainer.getUsername();
        DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration(postgreContainer.getJdbcUrl()
        ,username,password);
        carDao = new CarDaoImpl(dataBaseConfiguration);
    }


    @AfterAll
    public static void closeConnection(){
        postgreContainer.close();
    }

    @Test
    @DisplayName("Машина добавляется в базу данных")
    void addCarTest(){
        String brand = "TEST";
        carDao.addCar(
                Car.builder()
                .brand(brand)
                .build()
        );
        Assertions.assertTrue(carDao.getAllCars().stream().anyMatch(x -> x.getBrand().equals(brand)));
    }

    @Test
    @DisplayName("Машина удаляется из базы данных")
    void deleteCarTest(){
        int id = 1;
        carDao.deleteCar(id);
        Assertions.assertNull(carDao.getCar(id));
    }

    @Test
    @DisplayName("Машина обновляется в базе данных")
    void updateCarTest(){
        int id = 2;
        Car carFromDao = carDao.getCar(id);
        Car car = Car.builder()
        .id(id)
        .brand("UPDATE")
        .price(200)
        .model("UPDATE")
        .build();
        carDao.updateCar(car);
        Assertions.assertNotEquals(carFromDao,carDao.getCar(id));
    }

    @Test
    @DisplayName("Машина достается из базы данных")
    void getCarTest(){
        int id = 2;
        Assertions.assertNotNull(carDao.getCar(2));
    }
}
