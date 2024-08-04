package carshop_service.service;

import carshop_service.entity.Log;

import java.io.IOException;
import java.util.List;

public interface LogService {
    void addLog(Log log);
    List<Log> getAllLogs();
    void exportLogsInTextFile() throws IOException;
}
