package carshop_service.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Order {
    private static int globalId;
    private int id;
    private int carId;
    private int clientId;
    private String state;
    private String type;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Order(int carId, int clientId, String state, String type,LocalDateTime date) {
        this.carId = carId;
        this.clientId = clientId;
        this.state = state;
        this.id = globalId;
        this.type = type;
        this.date = date;
        globalId++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Айди клиента = " + clientId + "\n" +
                " Айди машины = " + carId + "\n" +
                " Состояние = " + state + "\n" +
                " Тип = " + type + "\n" +
                " Дата = " + date + "\n" +
                "----------------------------\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && carId == order.carId && clientId == order.clientId
                && Objects.equals(state, order.state) && Objects.equals(type, order.type)
                && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carId, clientId, state, type,date);
    }
}
