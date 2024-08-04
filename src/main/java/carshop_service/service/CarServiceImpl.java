package carshop_service.service;

import carshop_service.dao.CarDao;
import carshop_service.dao.CarDaoImpl;
import carshop_service.entity.Car;

import java.util.List;

public class CarServiceImpl implements CarService {
    private CarDao carDao;

    public CarServiceImpl(){
        this.carDao = new CarDaoImpl();
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    @Override
    public void addCar(Car car) {
        this.carDao.addCar(car);
    }

    @Override
    public Car getCar(int id) {
        return this.carDao.getCar(id);
    }

    @Override
    public void deleteCar(Car car) {
        this.carDao.deleteCar(car);
    }

    @Override
    public void updateCar(Car car) {
        this.carDao.updateCar(car);
    }

}
