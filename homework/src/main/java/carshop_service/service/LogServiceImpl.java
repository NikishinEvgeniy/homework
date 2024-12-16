package carshop_service.service;

import carshop_service.dao.LogDao;
import carshop_service.dto.LogDto;
import carshop_service.entity.Log;
import carshop_service.mapper.LogMapper;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogMapper logMapper = LogMapper.INSTANCE;
    private final LogDao logDao;

    @Override
    public void addLog(Log log) {
        this.logDao.addLog(log);
    }

    @Override
    public List<LogDto> getAllLogs() {
        return logMapper.listLogToListLogDto(this.logDao.getAllLogs());
    }

    @Override
    public void exportLogsInTextFile() throws IOException {
        PrintWriter fileWriter = new PrintWriter("/webapp/log/log.txt");
        List<LogDto> logs = getAllLogs();
        logs.forEach(fileWriter::println);
        fileWriter.flush();
        fileWriter.close();
    }
}
