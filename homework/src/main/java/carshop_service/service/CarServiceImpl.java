package carshop_service.service;

import carshop_service.dao.CarDao;
import carshop_service.dto.CarDto;
import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.mapper.CarMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper = CarMapper.INSTANCE;
    private final CarDao carDao;

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
