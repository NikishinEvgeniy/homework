package carshop_service.entity;

import java.time.LocalDateTime;

public class StandartOrderBuilder implements OrderBuilder {

    private int id;
    private int carId;
    private int clientId;
    private String state;
    private String type;
    private LocalDateTime date;


    @Override
    public OrderBuilder id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderBuilder carId(int carId) {
        this.carId = carId;
        return this;
    }

    @Override
    public OrderBuilder clientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public OrderBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public OrderBuilder type(String type) {
        this.type = type;
        return this;
    }

    @Override
    public OrderBuilder dateTime(LocalDateTime date) {
        this.date = date;
        return this;
    }

    @Override
    public Order build() {
        return new Order(this.id,this.carId,this.clientId,this.state,this.type,this.date);
    }
}
