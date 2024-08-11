package carshop_service.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Log {

    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private String action;

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
