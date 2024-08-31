package carshop.service.controller;

import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ya.lab.timebale_starter.annotation.Timebale;

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
@Tag(
        name = "Машины",
        description = "Все методы для работы с машинами в системе"
)
public class CarController extends HttpServlet {

    private final CarService carService;
    private final JsonHandler jsonHandler;

    @Autowired
    public CarController(CarService carService, JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
        this.carService = carService;
    }

    @Timebale
    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о всех существующих машинах")
    public ResponseEntity<List<CarDto>> getAllCars() throws DataBaseEmptyException {
        List<CarDto> allCars = carService.getAllCars();
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @Timebale
    @GetMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о существующей машине по идентификатору")
    public ResponseEntity<CarDto> getCar(
            @Parameter(description = "Идентификатор машины")
            @PathVariable(name = "id") int id) throws NoSuchEntityException {
        CarDto car = carService.getCar(id);
        return new ResponseEntity<>(car,HttpStatus.OK);
    }

    @Timebale
    @PutMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить информацию о существующей машине")
    public ResponseEntity<Map<String,String>> updateCar(
            @Parameter(description = "Обновленная машина, с нужным идентификатором")
            @RequestBody Car car) throws NoSuchEntityException {
        carService.updateCar(car);
        return  jsonHandler.successJson();
    }

    @Timebale
    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить информацию о машине")
    public ResponseEntity<Map<String,String>> addCar(
            @Parameter(description = "Созданная машина")
            @RequestBody Car car) throws DuplicateEntityException {
        carService.addCar(car);
        return  jsonHandler.successJson();
    }

    @Timebale
    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить информацию о машине")
    public ResponseEntity<Map<String,String>> deleteCar(
            @Parameter(description = "Идентификатор машины")
            @PathVariable(name = "id") int id) throws NoSuchEntityException {
        carService.deleteCar(id);
        return  jsonHandler.successJson();
    }
}
