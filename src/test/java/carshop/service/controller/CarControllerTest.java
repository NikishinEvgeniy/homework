package carshop.service.controller;

import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.handler.JsonHandler;
import carshop.service.handler.JsonHandlerImpl;
import carshop.service.service.CarService;
import carshop.service.service.CarServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CarControllerTest {

    private MockMvc mockMvc;
    private JsonHandler jsonHandler;
    private CarService carService;
    private ObjectMapper objectMapper;
    private final String contentType = "application/json";

    @BeforeEach
    void setUp() throws Exception{
        this.objectMapper = new ObjectMapper();
        this.jsonHandler = new JsonHandlerImpl();
        this.carService = Mockito.mock(CarServiceImpl.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carService,jsonHandler)).build();
    }

    @Test
    @DisplayName("GET(/api/cars) -> получение машин (JSON) из базы данных")
    public void getAllCarsTest() throws Exception {
        List<CarDto> cars = List.of(CarDto.builder().brand("TEST").build());
        Mockito.when(carService.getAllCars()).thenReturn(cars);
        this.mockMvc.perform(get("/api/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("GET(/api/cars/{id}) -> получение машины (JSON) из базы данных")
    public void getCarTest() throws Exception {
        int id = 10;
        CarDto carTest = CarDto.builder().brand("TEST").id(id).build();
        Mockito.when(carService.getCar(id)).thenReturn(carTest);
        this.mockMvc.perform(get("/api/cars/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("POST(/api/cars) -> добавление машины (JSON) в базу данных")
    public void addCarTest() throws Exception {
        Car car = Car.builder()
                .condition("condition1")
                .build();
        doNothing().when(carService).addCar(car);
        this.mockMvc.perform(
                        post("/api/cars")
                                .content(objectMapper.writeValueAsString(car))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("PUT(/api/cars) -> обновление машины (JSON) в базе данных")
    public void updateCarTest() throws Exception {
        Car car = Car.builder()
                .condition("condition1")
                .build();
        doNothing().when(carService).updateCar(car);
        this.mockMvc.perform(
                        put("/api/cars")
                                .content(objectMapper.writeValueAsString(car))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("DELETE(/api/cars) -> обновление машины (JSON) в базе данных")
    public void deleteCarTest() throws Exception {
        int id = 1000;
        doNothing().when(carService).deleteCar(id);
        this.mockMvc.perform(
                        delete("/api/cars/{id}",id)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}
