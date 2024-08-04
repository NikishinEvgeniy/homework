package carshop_service.dao;

import carshop_service.constant.LogAction;
import carshop_service.entity.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogDaoTest {
    private LogDaoImpl logDao = new LogDaoImpl();

    @Test
    @DisplayName(" Ошибка при получение не существующего лога ")
    public void getLogTest_false(){
        Assertions.assertEquals(false,logDao.getAllLogs().stream().filter(x -> x.getId() == 3232).findFirst().isPresent());
    }



    @Test
    @DisplayName(" Добавление нового лога ")
    public void saveLogTest_true(){
        Log log = new Log(1, LogAction.ADD_CLIENT_ACTION);
        logDao.addLog(log);
        Assertions.assertEquals(log,logDao.getAllLogs().stream().filter(x -> x.equals(log)).findFirst().get());
    }

}
