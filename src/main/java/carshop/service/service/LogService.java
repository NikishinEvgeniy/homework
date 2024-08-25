package carshop.service.service;


import carshop.service.dto.LogDto;
import carshop.service.entity.Log;

import java.io.IOException;
import java.util.List;

public interface LogService {
    void addLog(Log log);
    List<LogDto> getAllLogs();
    void exportLogsInTextFile() throws IOException;
}
