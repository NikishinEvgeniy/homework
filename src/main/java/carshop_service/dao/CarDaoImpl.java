package carshop_service.dao;

import carshop_service.entity.Car;

import java.util.HashMap;
import java.util.List;

public class CarDaoImpl implements CarDao {

    private HashMap<Integer, Car> cars;

    public CarDaoImpl(){
        this.cars = new HashMap<>();
        Car car1 = new Car("Brand1","Model1",100,10,"GOOD1");
        Car car2 = new Car("Brand2","Model2",200,20,"GOOD2");
        Car car3 = new Car("Brand3","Model3",300,30,"GOOD3");
        Car car4 = new Car("Brand4","Model4",400,40,"GOOD4");
        this.cars.put(car1.getId(),car1);
        this.cars.put(car2.getId(),car2);
        this.cars.put(car3.getId(),car3);
        this.cars.put(car4.getId(),car4);
    }

    @Override
    public List<Car> getAllCars() {
        return cars.values().stream().toList();
    }

    @Override
    public void addCar(Car car) {
        cars.put(car.getId(),car);
    }

    @Override
    public Car getCar(int id) {
        return this.cars.get(id);
    }

    @Override
    public void deleteCar(Car car) {
        this.cars.remove(car.getId());
    }

    @Override
    public void updateCar(Car car) {
        this.cars.put(car.getId(),car);
    }

}
