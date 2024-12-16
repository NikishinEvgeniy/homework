package carshop.service.service;

import carshop.service.dao.CarDao;
import carshop.service.dao.CarDaoImpl;
import carshop.service.entity.Car;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.mapper.CarMapper;
import carshop.service.mapper.CarMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

public class CarServiceTest {

    private CarDao carDao;
    private CarService carService;
    private CarMapper carMapper;

    public CarServiceTest(){
        this.carMapper = Mockito.mock(CarMapperImpl.class);
        this.carDao = Mockito.mock(CarDaoImpl.class);
        this.carService = new CarServiceImpl(carDao,carMapper);
    }


    @Test
    @DisplayName(" Ошибка, при получении списка машин из БД  (список пуст)")
    public void getAllCarsTestException(){
        Mockito.when(carDao.getAllCars())
                .thenReturn(new LinkedList<>());
        Assertions.assertThrows(DataBaseEmptyException.class,()->carService.getAllCars());
    }

    @Test
    @DisplayName(" Ошибка, при получении машины из БД (машина не найден) ")
    public void getCarTestException(){
        int id = 10;
        Mockito.when(carDao.getCar(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->carService.getCar(id));
    }

    @Test
    @DisplayName(" Ошибка, при добавлении машины в БД (дубликат) ")
    public void addCarTestException(){
        int id = 10;
        Car car = Car.builder()
                .id(id)
                .build();
        Mockito.when(carDao.getCar(car.getId()))
                .thenReturn(car);
        Assertions.assertThrows(DuplicateEntityException.class,()->carService.addCar(car));
    }

    @Test
    @DisplayName(" Ошибка, при удалении машины из БД (машина не найден) ")
    public void deleteCarTestException(){
        int id = 10;
        Mockito.when(carDao.getCar(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->carService.deleteCar(id));
    }

    @Test
    @DisplayName(" Ошибка, при обновлении машины в БД (машина не найден) ")
    public void updateCarTestException(){
        int id = 10;
        Car car = Car.builder()
                .id(id)
                .build();
        Mockito.when(carDao.getCar(id))
                .thenReturn(null);
        Assertions.assertThrows(NoSuchEntityException.class,()->carService.updateCar(car));
    }
}
