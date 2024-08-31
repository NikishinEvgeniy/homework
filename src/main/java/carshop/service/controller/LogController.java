package carshop.service.controller;

import carshop.service.dto.LogDto;
import carshop.service.entity.Log;
import carshop.service.handler.JsonHandler;
import carshop.service.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ya.lab.timebale_starter.annotation.Timebale;

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
@Tag(
        name = "Аудиты",
        description = "Все методы для работы с аудитами в системе"
)public class LogController extends HttpServlet {

    private final LogService logService;
    private final JsonHandler jsonHandler;

    @Autowired
    public LogController(LogService logService, JsonHandler jsonHandler) {
        this.logService = logService;
        this.jsonHandler = jsonHandler;
    }

    @Timebale
    @GetMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о всех существующих аудитах пользователя")
    public ResponseEntity<List<LogDto>> getAllLogs(){
        List<LogDto> allLogs = logService.getAllLogs();
        return new ResponseEntity<>(allLogs, HttpStatus.OK);
    }

    @Timebale
    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить, совершенный пользователем аудит действия")
    public ResponseEntity<Map<String,String>> addLog(
            @Parameter(description = "Созданный аудит действия")
            @RequestBody Log log){
        logService.addLog(log);
        return jsonHandler.successJson();
    }

    @Timebale
    @GetMapping("/logs/export")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Экспортировать аудиты действий из базы данных в файл")
    public ResponseEntity<Map<String,String>> exportLogs() throws IOException {
        logService.exportLogsInTextFile();
        return jsonHandler.successJson();
    }

}
