package carshop.service.dto;

import carshop.service.constant.OrderState;
import carshop.service.constant.OrderType;
import lombok.*;

import java.time.LocalDateTime;
/**
 * Data Transfer Object, а именно Order. Нужен для обмена информацией со view
 */
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
