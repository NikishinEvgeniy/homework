package carshop_service.dao;

import carshop_service.entity.Log;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface LogDao {
    void addLog(Log log);
    List<Log> getAllLogs();
}
