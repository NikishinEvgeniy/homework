package carshop_service.dao;

import carshop_service.entity.Log;

import java.util.HashMap;
import java.util.List;

public class LogDaoImpl implements LogDao {
    private HashMap<Integer, Log> logs;

    public LogDaoImpl(){
        this.logs = new HashMap<>();
    }

    public void addLog(Log log){
        this.logs.put(log.getId(),log);
    }

    @Override
    public List<Log> getAllLogs() {
        return this.logs.values().stream().toList();
    }

}
