package carshop_service.dto;

import carshop_service.constant.LogAction;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {

    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private LogAction action;

}
