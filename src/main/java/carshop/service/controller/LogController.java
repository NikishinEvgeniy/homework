package carshop.service.controller;

import carshop.service.dto.LogDto;
import carshop.service.entity.Log;
import carshop.service.handler.JsonHandler;
import carshop.service.service.LogService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, сюда приходят обработанные DispatcherServlet запросы
 * Выполненный в REST стиле.
 * Доступные запросы:
 * * GET /api/logs -> получение всех логов из базы данных
 * GET /api/logs/export -> экспортировать логи в файл
 * POST /api/logs -> добавить лог в базу данных
 */
@RestController
@RequestMapping("/api")
public class LogController extends HttpServlet {

    private final LogService logService;
    private final JsonHandler jsonHandler;

    @Autowired
    public LogController(LogService logService, JsonHandler jsonHandler) {
        this.logService = logService;
        this.jsonHandler = jsonHandler;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogDto>> getAllLogs(){
        List<LogDto> allLogs = logService.getAllLogs();
        return new ResponseEntity<>(allLogs, HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<Map<String,String>> addLog(@RequestBody Log log){
        logService.addLog(log);
        return jsonHandler.successJson();
    }

    @GetMapping("/logs/export")
    public ResponseEntity<Map<String,String>> exportLogs() throws IOException {
        logService.exportLogsInTextFile();
        return jsonHandler.successJson();
    }

}
