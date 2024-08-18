package carshop_service.service;

import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import java.util.List;

/**
 * Интерфейс, нужен для правильного соблюдения принципа DI
 */
public interface CarService {
    List<Car> getAllCars() throws DataBaseEmptyException;
    void addCar(Car car) throws DuplicateEntityException;
    Car getCar(int id) throws NoSuchEntityException;
    void deleteCar(int id) throws NoSuchEntityException;
    void updateCar(Car car) throws NoSuchEntityException;
}
