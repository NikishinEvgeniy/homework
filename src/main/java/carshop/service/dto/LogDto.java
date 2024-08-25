package carshop.service.dto;

import carshop.service.constant.LogAction;
import lombok.*;

import java.time.LocalDateTime;
/**
 * Data Transfer Object, а именно Log. Нужен для обмена информацией со view
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {

    private LocalDateTime dateTime;
    private int clientId;
    private LogAction action;

}
