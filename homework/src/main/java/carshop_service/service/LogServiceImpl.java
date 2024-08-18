package carshop_service.service;

import carshop_service.dao.LogDao;
import carshop_service.entity.Log;
import lombok.AllArgsConstructor;
import java.io.*;
import java.util.List;

/**
 * Промежуточное звено между приложением и dao
 * Обрабатывает ошибки, которые могут возникнуть при работе с log dao
 */
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogDao logDao;

    @Override
    public void addLog(Log log) {
        this.logDao.addLog(log);
    }

    @Override
    public List<Log> getAllLogs() {
        return this.logDao.getAllLogs();
    }

    @Override
    public void exportLogsInTextFile() throws IOException {
        PrintWriter fileWriter = new PrintWriter("src/main/resources/log.txt");
        List<Log> logs = getAllLogs();
        logs.forEach(fileWriter::println);
        fileWriter.flush();
        fileWriter.close();
    }
}
