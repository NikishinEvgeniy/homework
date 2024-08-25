package carshop.service.controller;

import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.service.OrderService;
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
 * GET /api/orders -> получение всех заказов из базы данных
 * GET /api/orders/{id} -> получение заказа из базы данных
 * POST /api/orders -> добавить заказ в базу данных
 * DELETE /api/orders/{id} -> удалить заказ из базы данных
 * PUT /api/orders -> изменить заказ в базе данных
 */
@RestController
@RequestMapping("/api")
public class OrderController extends HttpServlet {

    private final OrderService orderService;
    private final JsonHandler jsonHandler;

    @Autowired
    public OrderController(OrderService orderService, JsonHandler jsonHandler) {
        this.orderService = orderService;
        this.jsonHandler = jsonHandler;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() throws DataBaseEmptyException {
        List<OrderDto> allOrders = orderService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable(name = "id") int id) throws NoSuchEntityException {
        OrderDto order = orderService.getOrder(id);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/orders")
    public ResponseEntity<Map<String,String>> updateOrder(@RequestBody Order order) throws NoSuchEntityException {
        orderService.updateOrder(order);
        return jsonHandler.successJson();
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String,String>> addOrder(@RequestBody Order order) throws DuplicateEntityException {
        orderService.addOrder(order);
        return jsonHandler.successJson();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Map<String,String>> deleteOrder(@PathVariable(name = "id") int id) throws NoSuchEntityException {
        orderService.deleteOrder(id);
        return jsonHandler.successJson();
    }
}
