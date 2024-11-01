package carshop.service.service;

import carshop.service.mapper.LogMapper;
import carshop.service.dao.LogDao;
import carshop.service.dto.LogDto;
import carshop.service.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Сервис, обрабатывающий ошибки, при работе с базой данных
 */
@Service
public class LogServiceImpl implements LogService {

    private final LogMapper logMapper = LogMapper.INSTANCE;
    private final LogDao logDao;

    @Autowired
    public LogServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

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
