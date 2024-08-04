package carshop_service.dao;

import carshop_service.entity.Log;

import java.util.List;

public interface LogDao {
    void addLog(Log log);
    List<Log> getAllLogs();
}
