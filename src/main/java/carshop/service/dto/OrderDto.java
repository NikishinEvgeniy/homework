package carshop.service.dto;

import carshop.service.constant.OrderState;
import carshop.service.constant.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Информация о заказе")
public class OrderDto {

    @Schema(description = "Идентификатор")
    private int id;
    @Schema(description = "Идентификатор машины")
    private int carId;
    @Schema(description = "Идентификатор клиента")
    private int clientId;
    @Schema(description = "Состояние заказа")
    private OrderState state;
    @Schema(description = "Тип заказа")
    private OrderType type;
    @Schema(description = "Время заказа")
    private LocalDateTime dateTime;

}
