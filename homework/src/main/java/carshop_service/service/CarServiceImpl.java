package carshop_service.service;

import carshop_service.dao.CarDao;
import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Промежуточное звено между приложением и dao
 * Обрабатывает ошибки, которые могут возникнуть при работе с car dao
 */
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    @Override
    public List<Car> getAllCars() throws DataBaseEmptyException {
        List<Car> cars = carDao.getAllCars();
        if(cars.isEmpty()) throw new DataBaseEmptyException("Car");
        return cars;
    }

    @Override
    public void addCar(Car car) throws DuplicateEntityException {
        if(carDao.getCar(car.getId()) != null) throw new DuplicateEntityException("Car");
        this.carDao.addCar(car);
    }

    @Override
    public Car getCar(int id) throws NoSuchEntityException {
        Car car = carDao.getCar(id);
        if(car == null) throw new NoSuchEntityException("Car");
        return car;
    }

    @Override
    public void deleteCar(int id) throws NoSuchEntityException {
        Car car = carDao.getCar(id);
        if(car == null) throw new NoSuchEntityException("Car");
        this.carDao.deleteCar(id);
    }

    @Override
    public void updateCar(Car car) throws NoSuchEntityException {
        if(carDao.getCar(car.getId()) == null) throw new NoSuchEntityException("Car");
        this.carDao.updateCar(car);
    }

}
