package carshop_service.dao;

import carshop_service.entity.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CarDaoTest {
    private CarDaoImpl carDao = new CarDaoImpl();


    @Test
    @DisplayName(" Ошибка при получение машины ")
    public void getCarTest_false(){
        Assertions.assertEquals(null,carDao.getCar(3232));
    }



    @Test
    @DisplayName(" Добавление новой машины ")
    public void saveCarTest_true(){
        Car car = new Car("brand109","model103",1000,32,"GOOD");
        carDao.addCar(car);
        Assertions.assertEquals(car,carDao.getCar(car.getId()));
    }

    @Test
    @DisplayName(" Обновление информации о машине ")
    public void updateCarTest_true(){
        Car car = new Car("brand109","model103",1000,32,"GOOD");
        carDao.addCar(car);
        car.setPrice(3232.32);
        carDao.updateCar(car);
        Assertions.assertEquals(car,carDao.getCar(car.getId()));
    }

    @Test
    @DisplayName(" Удаление информации о машине ")
    public void deleteCarTest_true(){
        Car car = new Car("brand109","model103",1000,32,"GOOD");
        carDao.addCar(car);
        carDao.deleteCar(car);
        Assertions.assertEquals(null,carDao.getCar(car.getId()));
    }

}
