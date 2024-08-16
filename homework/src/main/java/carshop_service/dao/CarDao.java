package carshop_service.dao;

import carshop_service.entity.Car;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface CarDao {
    List<Car> getAllCars();
    void addCar(Car car);
    Car getCar(int id);
    void deleteCar(int id);
    void updateCar(Car car);
}
