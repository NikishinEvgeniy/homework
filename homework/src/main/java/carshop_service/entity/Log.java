package carshop_service.entity;

import carshop_service.constant.LogAction;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность log
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Log {

    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private LogAction action;

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Клиент айди = " + clientId + "\n" +
                " Действие = " + action + "\n" +
                " Дата = " + dateTime + "\n"+
                "----------------------------\n";
    }
}
