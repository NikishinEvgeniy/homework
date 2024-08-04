package carshop_service.dao;

import carshop_service.entity.Car;

import java.util.List;

public interface CarDao {
    List<Car> getAllCars();
    void addCar(Car car);
    Car getCar(int id);
    void deleteCar(Car car);
    void updateCar(Car car);
}
