package carshop.service.mapper;

import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);
    List<OrderDto> listOfOrdersToOrdersDto(List<Order> orders);
}
