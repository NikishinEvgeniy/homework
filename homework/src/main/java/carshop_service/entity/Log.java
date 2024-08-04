package carshop_service.entity;

import java.time.LocalDateTime;
import java.util.Objects;

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
