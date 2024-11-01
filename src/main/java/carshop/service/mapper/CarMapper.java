package carshop.service.mapper;

import carshop.service.dto.CarDto;
import carshop.service.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto carToCartDto(Car car);
    List<CarDto> listOfCarsToCarsDto(List<Car> car);
}
