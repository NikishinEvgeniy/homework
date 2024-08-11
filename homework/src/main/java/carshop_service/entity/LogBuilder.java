package carshop_service.entity;

import java.time.LocalDateTime;

public interface LogBuilder {
    LogBuilder dateTime(LocalDateTime dateTime);
    LogBuilder id(int id);
    LogBuilder clientId(int id);
    LogBuilder action(String action);
    Log build();
}
