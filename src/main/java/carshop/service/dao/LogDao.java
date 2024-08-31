package carshop.service.dao;


import carshop.service.entity.Log;

import java.util.List;

/**
 * Интерфейс нужный для соблюдения принципов правильного DI, описывает DAO поведение объекта
 */
public interface LogDao {
    void addLog(Log log);
    List<Log> getAllLogs();
}
