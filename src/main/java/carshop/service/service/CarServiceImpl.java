package carshop.service.service;

import carshop.service.dao.CarDao;
import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Сервис, обрабатывающий ошибки, при работе с базой данных
 */
@Service
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;
    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(CarDao carDao, CarMapper carMapper) {
        this.carMapper = carMapper;
        this.carDao = carDao;
    }

    @Override
    public List<CarDto> getAllCars() throws DataBaseEmptyException {
        List<Car> cars = carDao.getAllCars();
        if (cars.isEmpty()) throw new DataBaseEmptyException("Car");
        return carMapper.listOfCarsToCarsDto(cars);
    }

    @Override
    public void addCar(Car car) throws DuplicateEntityException {
        if (carDao.getCar(car.getId()) != null) throw new DuplicateEntityException("Car");
        this.carDao.addCar(car);
    }

    @Override
    public CarDto getCar(int id) throws NoSuchEntityException {
        Car car = carDao.getCar(id);
        if (car == null) throw new NoSuchEntityException("Car");
        return carMapper.carToCartDto(car);
    }

    @Override
    public void deleteCar(int id) throws NoSuchEntityException {
        Car car = carDao.getCar(id);
        if (car == null) throw new NoSuchEntityException("Car");
        this.carDao.deleteCar(id);
    }

    @Override
    public void updateCar(Car car) throws NoSuchEntityException {
        if (carDao.getCar(car.getId()) == null) throw new NoSuchEntityException("Car");
        this.carDao.updateCar(car);
    }

}
