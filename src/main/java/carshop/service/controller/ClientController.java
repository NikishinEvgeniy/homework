package carshop.service.controller;

import carshop.service.dto.ClientDto;
import carshop.service.entity.Client;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ya.lab.timebale_starter.annotation.Timebale;

import java.util.List;
import java.util.Map;

/**
 * Контроллер, сюда приходят обработанные DispatcherServlet запросы
 * Выполненный в REST стиле.
 * Доступные запросы:
 * GET /api/clients -> получение всех клиентов из базы данных
 * GET /api/clients/{id} -> получение клиента из базы данных
 * POST /api/clients -> добавить клиента в базу данных
 * PUT /api/clients -> изменить клиента в базе данных
 */
@RestController
@RequestMapping("/api")
@Tag(
        name = "Клиенты",
        description = "Все методы для работы с клиентами в системе"
)
public class ClientController extends HttpServlet {

    private final ClientService clientService;
    private final JsonHandler jsonHandler;

    @Autowired
    public ClientController(ClientService clientService, JsonHandler jsonHandler) {
        this.clientService = clientService;
        this.jsonHandler = jsonHandler;
    }

    @Timebale
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о всех существующих клиентах")
    public ResponseEntity<List<ClientDto>> getAllClients() throws DataBaseEmptyException {
        List<ClientDto> allClients = clientService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    @Timebale
    @GetMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить информацию о существующей клиенте по идентификатору")
    public ResponseEntity<ClientDto> getClient(
            @Parameter(description = "Идентификатор клиента")
            @PathVariable(name = "id") int id) throws NoSuchEntityException {
        ClientDto client = clientService.getClient(id);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }

    @Timebale
    @PutMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить информацию о существующем клиенте")
    public ResponseEntity<Map<String,String>> updateClient(
            @Parameter(description = "Обновленный клиент, с нужным идентификатором")
            @RequestBody Client client) throws NoSuchEntityException {
        clientService.updateClient(client);
        return jsonHandler.successJson();
    }

    @Timebale
    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Добавить информацию о клиенте")
    public ResponseEntity<Map<String,String>> addClient(
            @Parameter(description = "Созданный клиент")
            @RequestBody Client client) throws DuplicateEntityException {
        clientService.addClient(client);
        return jsonHandler.successJson();
    }
}
