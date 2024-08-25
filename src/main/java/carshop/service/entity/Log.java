package carshop.service.entity;

import carshop.service.constant.LogAction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@NoArgsConstructor
public class Log {

    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private LogAction action;
    private long executionDuration;

    @JsonCreator
    public Log(
            @JsonProperty(value = "clientId", required = true) int clientId,
            @JsonProperty(value = "action", required = true) LogAction action,
            @JsonProperty(value = "dateTime", required = true) LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.clientId = clientId;
        this.action = action;
    }

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Клиент айди = " + clientId + "\n" +
                " Действие = " + action + "\n" +
                " Дата = " + dateTime + "\n"+
                " Продолжительность выполнения: " + executionDuration + "\n"+
                "----------------------------\n";
    }
}
