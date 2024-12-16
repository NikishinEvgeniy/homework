package carshop_service.servlet;

import carshop_service.dto.CarDto;
import carshop_service.entity.Car;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.handler.JasonHandler;
import carshop_service.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CarServletTest {

    @Mock
    private CarService carService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JasonHandler jasonHandler;

    @InjectMocks
    private CarServlet carServlet;

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
        CarDto car1 = CarDto.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2020)
                .build();
        CarDto car2 = CarDto.builder()
                .model("model2")
                .brand("brand2")
                .yearOfRelease(2021)
                .build();
        List<CarDto> allCars = Arrays.asList(car1, car2);
        when(carService.getAllCars()).thenReturn(allCars);
        byte[] jsonBody = "jsonBody".getBytes();
        when(objectMapper.writeValueAsBytes(allCars)).thenReturn(jsonBody);
        carServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, jsonBody);
    }

    @Test
    public void testDoGetDataBaseEmptyException() throws Exception {
        when(carService.getAllCars()).thenThrow(new DataBaseEmptyException("Cars"));
        byte[] jsonBody = "errorJson".getBytes();
        when(objectMapper.writeValueAsBytes(any())).thenReturn(jsonBody);
        carServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, jsonBody);
    }

    @Test
    public void testDoPutSuccess() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        when(objectMapper.writeValueAsBytes(any())).thenReturn(successJson);
        carServlet.doPut(request, response);
        verify(carService).updateCar(car);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPutNoSuchEntityException() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] errorJson = "{ status : error , message : Car not found }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        doThrow(new NoSuchEntityException("Car not found")).when(carService).updateCar(car);
        when(objectMapper.writeValueAsBytes(any())).thenReturn(errorJson);
        carServlet.doPut(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        when(objectMapper.writeValueAsBytes(any())).thenReturn(successJson);
        carServlet.doPost(request, response);
        verify(carService).addCar(car);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPostDuplicateEntityException() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] errorJson = "{ status : error , message : Car already exists }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        doThrow(new DuplicateEntityException("Car already exists")).when(carService).addCar(car);
        when(objectMapper.writeValueAsBytes(any())).thenReturn(errorJson);
        carServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }

    @Test
    public void testDoDeleteSuccess() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        when(objectMapper.writeValueAsBytes(any())).thenReturn(successJson);
        carServlet.doDelete(request, response);
        verify(carService).deleteCar(car.getId());
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoDeleteNoSuchEntityException() throws Exception {
        Car car = Car.builder()
                .model("model1")
                .brand("brand1")
                .yearOfRelease(2022)
                .build();
        String jsonBodyForUser = "{ model : model1 , brand1 : brand1 , yearOfRelease :2022}";
        byte[] errorJson = "{ status : error , message : Car not found }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Car.class)).thenReturn(car);
        doThrow(new NoSuchEntityException("Car not found")).when(carService).deleteCar(car.getId());
        when(objectMapper.writeValueAsBytes(any())).thenReturn(errorJson);
        carServlet.doDelete(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }
}
