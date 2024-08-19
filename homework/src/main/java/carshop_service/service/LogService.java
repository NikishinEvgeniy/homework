package carshop_service.service;

import carshop_service.dto.LogDto;
import carshop_service.entity.Log;

import java.io.IOException;
import java.util.List;

public interface LogService {
    void addLog(Log log);
    List<LogDto> getAllLogs();
    void exportLogsInTextFile() throws IOException;
}
