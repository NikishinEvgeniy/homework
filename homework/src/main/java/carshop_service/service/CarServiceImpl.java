package carshop_service.service;

import carshop_service.dao.CarDao;
import carshop_service.entity.Car;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private CarDao carDao;

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
    public void deleteCar(int id) {
        this.carDao.deleteCar(id);
    }

    @Override
    public void updateCar(Car car) {
        this.carDao.updateCar(car);
    }

}
