package carshop_service.dto;

import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int id;
    private int carId;
    private int clientId;
    private OrderState state;
    private OrderType type;
    private LocalDateTime dateTime;

}
