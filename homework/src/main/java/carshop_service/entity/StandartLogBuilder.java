package carshop_service.entity;

import java.time.LocalDateTime;

public class StandartLogBuilder implements LogBuilder {

    private int id;
    private LocalDateTime dateTime;
    private int clientId;
    private String action;

    @Override
    public LogBuilder dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public LogBuilder id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public LogBuilder clientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public LogBuilder action(String action) {
        this.action = action;
        return this;
    }

    @Override
    public Log build() {
        return new Log(this.id,this.dateTime,this.clientId,this.action);
    }
}
