package carshop.service.controller;

import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.service.OrderService;
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
 * GET /api/orders -> получение всех заказов из базы данных
 * GET /api/orders/{id} -> получение заказа из базы данных
 * POST /api/orders -> добавить заказ в базу данных
 * DELETE /api/orders/{id} -> удалить заказ из базы данных
 * PUT /api/orders -> изменить заказ в базе данных
 */
@RestController
@RequestMapping("/api")
@Tag(
        name = "Заказы",
        description = "Все методы для работы с заказами в системе"
)
public class OrderController extends HttpServlet {

    private final OrderService orderService;
    private final JsonHandler jsonHandler;

    @Autowired
    public OrderController(OrderService orderService, JsonHandler jsonHandler) {
        this.orderService = orderService;
        this.jsonHandler = jsonHandler;
    }

    @Timebale
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о всех существующих заказах")
    public ResponseEntity<List<OrderDto>> getAllOrders() throws DataBaseEmptyException {
        List<OrderDto> allOrders = orderService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @Timebale
    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о существующем заказе по идентификатору")
    public ResponseEntity<OrderDto> getOrder(
            @Parameter(description = "Идентификатор заказа")
            @PathVariable(name = "id") int id) throws NoSuchEntityException {
        OrderDto order = orderService.getOrder(id);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @Timebale
    @PutMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить информацию о существующем заказе")
    public ResponseEntity<Map<String,String>> updateOrder(
            @Parameter(description = "Обновленный заказ, с нужным идентификатором")
            @RequestBody Order order) throws NoSuchEntityException {
        orderService.updateOrder(order);
        return jsonHandler.successJson();
    }

    @Timebale
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить информацию о заказе")
    public ResponseEntity<Map<String,String>> addOrder(
            @Parameter(description = "Созданный заказ")
            @RequestBody Order order) throws DuplicateEntityException {
        orderService.addOrder(order);
        return jsonHandler.successJson();
    }

    @Timebale
    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить информацию о заказе")
    public ResponseEntity<Map<String,String>> deleteOrder(
            @Parameter(description = "Идентификатор заказа")
            @PathVariable(name = "id") int id) throws NoSuchEntityException {
        orderService.deleteOrder(id);
        return jsonHandler.successJson();
    }
}
