package carshop_service.entity;

import carshop_service.constant.ClientRole;
import carshop_service.constant.ClientState;
import carshop_service.constant.OrderState;
import carshop_service.constant.OrderType;
import carshop_service.exception.IncorrectRoleException;
import carshop_service.exception.IncorrectStateException;
import carshop_service.exception.IncorrectTypeException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Сущность order
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Order {

    private int id;
    private int carId;
    private int clientId;
    private OrderState state;
    private OrderType type;
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

    public static class OrderBuilder {
        private int id;
        private int carId;
        private int clientId;
        private OrderState state;
        private OrderType type;

        public OrderBuilder id(int id) {
            this.id = id;
            return this;
        }
        public OrderBuilder carId(int carId) {
            this.carId = carId;
            return this;
        }
        public OrderBuilder clientId(int clientId) {
            this.clientId = clientId;
            return this;
        }
        public OrderBuilder state(OrderState state) {
            this.state = state;
            return this;
        }
        public OrderBuilder type(OrderType type) {
            this.type = type;
            return this;
        }
        public OrderBuilder checkState() throws IncorrectStateException {
            if (Arrays.stream(OrderState.values()).noneMatch(x -> x.equals(this.state)))
                throw new IncorrectStateException();
            return this;
        }
        public OrderBuilder checkType() throws IncorrectTypeException {
            if (Arrays.stream(OrderType.values()).noneMatch(x -> x.equals(this.type)))
                throw new IncorrectTypeException();
            return this;
        }
        public Order build() {
            return new Order(this.id,this.carId,this.clientId,this.state,this.type,LocalDateTime.now());
        }
    }
}
