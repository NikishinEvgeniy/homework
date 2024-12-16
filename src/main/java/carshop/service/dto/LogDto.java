package carshop.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ya.lab.loggable_starter.constant.LogAction;

import java.time.LocalDateTime;
/**
 * Data Transfer Object, а именно Log. Нужен для обмена информацией со view
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о аудите действия пользователя")
public class LogDto {

    @Schema(description = "Время активности ")
    private LocalDateTime dateTime;
    @Schema(description = " Сотрудник, сделавший действие ")
    private int clientId;
    @Schema(description = " Действие сотрудника")
    private LogAction action;

}
