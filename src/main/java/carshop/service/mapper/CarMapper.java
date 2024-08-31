package carshop.service.mapper;

import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto carToCartDto(Car car);
    List<CarDto> listOfCarsToCarsDto(List<Car> car);
}
