package carshop.service.dao;


import carshop.service.annotation.Loggable;
import carshop.service.constant.LogAction;
import carshop.service.entity.Car;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface CarDao {
    List<Car> getAllCars();
    @Loggable(action = LogAction.ADD_CAR_ACTION)
    void addCar(Car car);
    Car getCar(int id);
    @Loggable(action = LogAction.DELETE_CAR_ACTION)
    void deleteCar(int id);
    @Loggable(action = LogAction.UPDATE_CAR_ACTION)
    void updateCar(Car car);
}
