package carshop_service.service;

import carshop_service.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    void addCar(Car car);
    Car getCar(int id);
    void deleteCar(int id);
    void updateCar(Car car);
}
