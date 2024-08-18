package carshop_service.entity;

<<<<<<< Updated upstream
=======
import carshop_service.constant.LogAction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

>>>>>>> Stashed changes
import java.time.LocalDateTime;
import java.util.Objects;

<<<<<<< Updated upstream
=======
/**
 * Сущность log
 */
@Builder
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Stashed changes
public class Log {
    private static int globalId;
    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private String action;

    public Log(int clientId, String action) {
        this.clientId = clientId;
        this.action = action;
        this.dateTime = LocalDateTime.now();
        this.id = globalId++;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return id == log.id && clientId == log.clientId && Objects.equals(dateTime, log.dateTime) && Objects.equals(action, log.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, clientId, action);
    }

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
        return "----------------------------\n" +
                " Айди = " + id + "\n" +
                " Клиент айди = " + clientId + "\n" +
                " Действие = " + action + "\n" +
                " Дата = " + dateTime + "\n" +
                "----------------------------\n";
    }

}
