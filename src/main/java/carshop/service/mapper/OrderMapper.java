package carshop.service.mapper;

import carshop.service.dto.OrderDto;
import carshop.service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);
    List<OrderDto> listOfOrdersToOrdersDto(List<Order> orders);
}
