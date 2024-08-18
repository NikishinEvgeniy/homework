package carshop_service.service;

import carshop_service.dao.CarDao;
<<<<<<< Updated upstream
import carshop_service.dao.CarDaoImpl;
import carshop_service.entity.Car;
=======
import carshop_service.dto.CarDto;
import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.mapper.CarMapper;
import lombok.AllArgsConstructor;
>>>>>>> Stashed changes

import java.util.List;

public class CarServiceImpl implements CarService {
    private CarDao carDao;

<<<<<<< Updated upstream
    public CarServiceImpl(){
        this.carDao = new CarDaoImpl();
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    @Override
    public void addCar(Car car) {
=======
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
>>>>>>> Stashed changes
        this.carDao.addCar(car);
    }

    @Override
<<<<<<< Updated upstream
    public Car getCar(int id) {
        return this.carDao.getCar(id);
    }

    @Override
    public void deleteCar(Car car) {
        this.carDao.deleteCar(car);
    }

    @Override
    public void updateCar(Car car) {
=======
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
>>>>>>> Stashed changes
        this.carDao.updateCar(car);
    }

}
