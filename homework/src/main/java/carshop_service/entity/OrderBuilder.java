package carshop_service.entity;

import java.time.LocalDateTime;

public interface OrderBuilder {

    OrderBuilder id(int id);
    OrderBuilder carId(int carId);
    OrderBuilder clientId(int clientId);
    OrderBuilder state(String state);
    OrderBuilder type(String type);
    OrderBuilder dateTime(LocalDateTime date);
    Order build();
}
