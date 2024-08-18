package carshop_service.service;

import carshop_service.dto.CarDto;
import carshop_service.entity.Car;

import java.util.List;

public interface CarService {
<<<<<<< Updated upstream
    List<Car> getAllCars();
    void addCar(Car car);
    Car getCar(int id);
    void deleteCar(Car car);
    void updateCar(Car car);
=======
    List<CarDto> getAllCars() throws DataBaseEmptyException;
    void addCar(Car car) throws DuplicateEntityException;
    CarDto getCar(int id) throws NoSuchEntityException;
    void deleteCar(int id) throws NoSuchEntityException;
    void updateCar(Car car) throws NoSuchEntityException;
>>>>>>> Stashed changes
}
