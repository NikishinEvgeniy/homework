package carshop.service.controller;

import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.service.CarService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, сюда приходят обработанные DispatcherServlet запросы
 * Выполненный в REST стиле.
 * Доступные запросы:
 * GET /api/cars -> получение всех машин из базы данных
 * GET /api/cars/{id} -> получение машины из базы данных
 * POST /api/cars -> добавить машину в базу данных
 * DELETE /api/cars/{id} -> удалить машину из базы данных
 * PUT /api/cars -> изменить машину в базе данных
 */
@RestController
@RequestMapping("/api")
public class CarController extends HttpServlet {

    private final CarService carService;
    private final JsonHandler jsonHandler;

    @Autowired
    public CarController(CarService carService, JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() throws DataBaseEmptyException {
        List<CarDto> allCars = carService.getAllCars();
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable(name = "id") int id) throws NoSuchEntityException {
        CarDto car = carService.getCar(id);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }

    @PutMapping("/cars")
    public ResponseEntity<Map<String,String>> updateCar(@RequestBody Car car) throws NoSuchEntityException {
        carService.updateCar(car);
        return  jsonHandler.successJson();
    }

    @PostMapping("/cars")
    public ResponseEntity<Map<String,String>> addCar(@RequestBody Car car) throws DuplicateEntityException {
        carService.addCar(car);
        return  jsonHandler.successJson();
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Map<String,String>> deleteCar(@PathVariable(name = "id") int id) throws NoSuchEntityException {
        carService.deleteCar(id);
        return  jsonHandler.successJson();
    }
}
