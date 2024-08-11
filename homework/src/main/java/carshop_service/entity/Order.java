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
public class Order {

    private int id;
    private int carId;
    private int clientId;
    private String state;
    private String type;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Айди клиента = " + clientId + "\n" +
                " Айди машины = " + carId + "\n" +
                " Состояние = " + state + "\n" +
                " Тип = " + type + "\n" +
                " Дата = " + dateTime + "\n" +
                "----------------------------\n";
    }
}
