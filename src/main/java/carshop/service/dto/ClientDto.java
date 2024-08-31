package carshop.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
/**
 * Data Transfer Object, а именно Client. Нужен для обмена информацией со view
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о клиенте")
public class ClientDto {
    @Schema(description = "Идентификатор")
    private int id;
    @Schema(description = "Количество покупок")
    private int countOfBuy;
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Фамилия")
    private String surname;

}
