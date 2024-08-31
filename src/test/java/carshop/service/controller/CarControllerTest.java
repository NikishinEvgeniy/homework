package carshop.service.controller;

import carshop.service.configuration.ApplicationWebTestConfiguration;
import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import carshop.service.handler.JsonHandler;
import carshop.service.service.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@Import(ApplicationWebTestConfiguration.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonHandler jsonHandler;
    @MockBean
    private CarService carService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String contentType = "application/json";

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
