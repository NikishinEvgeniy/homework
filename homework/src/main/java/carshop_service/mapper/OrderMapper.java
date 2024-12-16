package carshop_service.mapper;

import carshop_service.dto.OrderDto;
import carshop_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);
    List<OrderDto> listOfOrdersToOrdersDto(List<Order> orders);
}
