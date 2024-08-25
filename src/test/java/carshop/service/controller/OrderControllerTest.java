package carshop.service.controller;

import carshop.service.constant.OrderType;
import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import carshop.service.handler.JsonHandler;
import carshop.service.handler.JsonHandlerImpl;
import carshop.service.service.OrderService;
import carshop.service.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    private MockMvc mockMvc;
    private JsonHandler jsonHandler;
    private OrderService orderService;
    private ObjectMapper objectMapper;
    private final String contentType = "application/json";

    @BeforeEach
    void setUp() throws Exception{
        this.objectMapper = new ObjectMapper();
        this.jsonHandler = new JsonHandlerImpl();
        this.orderService = Mockito.mock(OrderServiceImpl.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService,jsonHandler)).build();
    }
    @Test
    @DisplayName("GET(/api/orders) -> получение заказов (JSON) из базы данных")
    public void getAllOrdersTest() throws Exception {
        List<OrderDto> orders = List.of(OrderDto.builder().type(OrderType.SALE).build());
        Mockito.when(orderService.getAllOrders()).thenReturn(orders);
        this.mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("GET(/api/orders/{id}) -> получение заказа (JSON) из базы данных")
    public void getOrderTest() throws Exception {
        int id = 10;
        OrderDto orderTest = OrderDto.builder().type(OrderType.SALE).build();
        Mockito.when(orderService.getOrder(id)).thenReturn(orderTest);
        this.mockMvc.perform(get("/api/orders/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("POST(/api/orders) -> добавление заказа (JSON) в базу данных")
    public void addOrderTest() throws Exception {
        Order orderTest = new Order();
        doNothing().when(orderService).addOrder(orderTest);
        this.mockMvc.perform(
                        post("/api/orders")
                                .content(objectMapper.writeValueAsString(orderTest))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("PUT(/api/orders) -> обновление заказа (JSON) в базе данных")
    public void updateOrderTest() throws Exception {
        Order order = new Order();
        doNothing().when(orderService).updateOrder(order);
        this.mockMvc.perform(
                        put("/api/orders")
                                .content(objectMapper.writeValueAsString(order))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("DELETE(/api/orders/{id}) -> удаление заказа из базе данных")
    public void deleteOrderTest() throws Exception {
        int id = 1000;
        doNothing().when(orderService).deleteOrder(id);
        this.mockMvc.perform(
                        delete("/api/orders/{id}",id)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}