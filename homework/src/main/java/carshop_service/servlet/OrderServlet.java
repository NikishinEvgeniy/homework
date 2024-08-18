package carshop_service.servlet;

import carshop_service.annotation.Loggable;
import carshop_service.annotation.Timebale;
import carshop_service.application.ConfigLoader;
import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.JsonMessage;
import carshop_service.constant.LogAction;
import carshop_service.dao.OrderDaoImpl;
import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.handler.JasonHandler;
import carshop_service.handler.StandartJasonHandler;
import carshop_service.service.OrderService;
import carshop_service.service.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@Timebale
@WebServlet("/orders")
@AllArgsConstructor
public class OrderServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    private final JasonHandler jasonHandler;

    public OrderServlet() {
        ConfigLoader configLoader = new ConfigLoader("application.properties");
        this.objectMapper = new ObjectMapper();
        this.jasonHandler = new StandartJasonHandler(objectMapper);
        objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
        this.orderService = new OrderServiceImpl(
                new OrderDaoImpl(
                        new DataBaseConfiguration(
                                configLoader.getProperty("car_service.url"),
                                configLoader.getProperty("car_service.username"),
                                configLoader.getProperty("car_service.password"))
                ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] jsonBody;
        try {
            List<OrderDto> orderDtos = orderService.getAllOrders();
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBody = objectMapper.writeValueAsBytes(orderDtos);
        } catch (DataBaseEmptyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBody = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        }
        jasonHandler.sendTheJson(resp, jsonBody);
    }

    @Override
    @Loggable(action = LogAction.UPDATE_ORDER_ACTION)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        Order order = objectMapper.readValue(jsonBodyForUser, Order.class);
        byte[] jsonBodyForServer;
        try {
            orderService.updateOrder(order);
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (NoSuchEntityException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }

    @Override
    @Loggable(action = LogAction.ADD_ORDER_ACTION)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        Order order = objectMapper.readValue(jsonBodyForUser, Order.class);
        byte[] jsonBodyForServer;
        try {
            orderService.addOrder(order);
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (DuplicateEntityException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }

    @Override
    @Loggable(action = LogAction.DELETE_ORDER_ACTION)
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        Order order = objectMapper.readValue(jsonBodyForUser, Order.class);
        byte[] jsonBodyForServer;
        try {
            orderService.deleteOrder(order.getId());
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (NoSuchEntityException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }
}
