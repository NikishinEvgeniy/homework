package carshop_service.service;

import carshop_service.entity.Log;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс, нужен для правильного соблюдения принципа DI
 */
public interface LogService {
    void addLog(Log log);
    List<Log> getAllLogs();
    void exportLogsInTextFile() throws IOException;
}
