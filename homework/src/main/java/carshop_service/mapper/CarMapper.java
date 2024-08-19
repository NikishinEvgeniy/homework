package carshop_service.mapper;

import carshop_service.dto.CarDto;
import carshop_service.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto carToCartDto(Car car);
    List<CarDto> listOfCarsToCarsDto(List<Car> car);
}
