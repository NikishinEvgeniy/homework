package carshop.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object, а именно Car. Нужен для обмена информацией со view
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о машине")
public class CarDto {
    @Schema(description = "Идентификатор")
    private int id;
    @Schema(description = "Марка")
    private String brand;
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Цена")
    private double price;
    @Schema(description = "Год выпуска")
    private int yearOfRelease;
    @Schema(description = "Состояние")
    private String condition;

}
