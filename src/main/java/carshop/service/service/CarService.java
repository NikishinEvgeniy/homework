package carshop.service.service;


import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCars() throws DataBaseEmptyException;
    void addCar(Car car) throws DuplicateEntityException;
    CarDto getCar(int id) throws NoSuchEntityException;
    void deleteCar(int id) throws NoSuchEntityException;
    void updateCar(Car car) throws NoSuchEntityException;

}
