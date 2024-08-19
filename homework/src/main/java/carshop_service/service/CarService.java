package carshop_service.service;

import carshop_service.dto.CarDto;
import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCars() throws DataBaseEmptyException;
    void addCar(Car car) throws DuplicateEntityException;
    CarDto getCar(int id) throws NoSuchEntityException;
    void deleteCar(int id) throws NoSuchEntityException;
    void updateCar(Car car) throws NoSuchEntityException;

}
