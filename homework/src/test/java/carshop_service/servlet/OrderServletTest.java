package carshop_service.servlet;

import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.handler.JasonHandler;
import carshop_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderServletTest {
    @Mock
    private OrderService orderService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JasonHandler jasonHandler;

    @InjectMocks
    private OrderServlet orderServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private ByteArrayOutputStream responseOutputStream;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseOutputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b) throws IOException {
                responseOutputStream.write(b);
            }
        });
    }

    @Test
    public void testDoGetSuccess() throws Exception {
        OrderDto order1 = OrderDto.builder().id(1).clientId(2).build();
        OrderDto order2 = OrderDto.builder().id(3).clientId(4).build();
        List<OrderDto> allOrders = Arrays.asList(order1, order2);
        when(orderService.getAllOrders()).thenReturn(allOrders);
        byte[] jsonBody = "jsonBody".getBytes();
        when(objectMapper.writeValueAsBytes(allOrders)).thenReturn(jsonBody);
        orderServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, jsonBody);
    }

    @Test
    public void testDoGetDataBaseEmptyException() throws Exception {
        when(orderService.getAllOrders()).thenThrow(new DataBaseEmptyException("Orders"));
        byte[] jsonBody = "errorJson".getBytes();
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(jsonBody);
        orderServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, jsonBody);
    }

    @Test
    public void testDoPutSuccess() throws Exception {
        Order order1 = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order1);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        orderServlet.doPut(request, response);
        verify(orderService).updateOrder(order1);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPutNoSuchEntityException() throws Exception {
        Order order1 = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] errorJson = "{ status : error , message : Order not found }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order1);
        doThrow(new NoSuchEntityException("Order not found")).when(orderService).updateOrder(order1);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(errorJson);
        orderServlet.doPut(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        Order order = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        orderServlet.doPost(request, response);
        verify(orderService).addOrder(order);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPostDuplicateEntityException() throws Exception {
        Order order = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] errorJson = "{ status : error , message : Order already exists }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order);
        doThrow(new DuplicateEntityException("Order already exists")).when(orderService).addOrder(order);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(errorJson);
        orderServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }

    @Test
    public void testDoDeleteSuccess() throws Exception {
        Order order = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        orderServlet.doDelete(request, response);
        verify(orderService).deleteOrder(order.getId());
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoDeleteNoSuchEntityException() throws Exception {
        Order order = Order.builder().id(1).clientId(2).build();
        String jsonBodyForUser = "{ id : 1 , clientId : 2 }";
        byte[] errorJson = "{ status : error , message : Order not found }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Order.class)).thenReturn(order);
        doThrow(new NoSuchEntityException("Order not found")).when(orderService).deleteOrder(order.getId());
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(errorJson);
        orderServlet.doDelete(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }
}
