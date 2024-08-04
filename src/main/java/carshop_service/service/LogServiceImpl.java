package carshop_service.service;

import carshop_service.dao.LogDao;
import carshop_service.dao.LogDaoImpl;
import carshop_service.entity.Log;

import java.io.*;
import java.util.List;

public class LogServiceImpl implements LogService {
    private LogDao logDao;
    public LogServiceImpl(){
        this.logDao = new LogDaoImpl();
    }

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
