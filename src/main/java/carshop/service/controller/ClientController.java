package carshop.service.controller;

import carshop.service.dto.ClientDto;
import carshop.service.entity.Client;
import carshop.service.exception.DataBaseEmptyException;
import carshop.service.exception.DuplicateEntityException;
import carshop.service.exception.NoSuchEntityException;
import carshop.service.handler.JsonHandler;
import carshop.service.service.ClientService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class ClientController extends HttpServlet {

    private final ClientService clientService;
    private final JsonHandler jsonHandler;

    @Autowired
    public ClientController(ClientService clientService, JsonHandler jsonHandler) {
        this.clientService = clientService;
        this.jsonHandler = jsonHandler;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getAllClients() throws DataBaseEmptyException {
        List<ClientDto> allClients = clientService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable(name = "id") int id) throws NoSuchEntityException {
        ClientDto client = clientService.getClient(id);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }

    @PutMapping("/clients")
    public ResponseEntity<Map<String,String>> updateClient(@RequestBody Client client) throws NoSuchEntityException {
        clientService.updateClient(client);
        return jsonHandler.successJson();
    }

    @PostMapping("/clients")
    public ResponseEntity<Map<String,String>> addClient(@RequestBody Client client) throws DuplicateEntityException {
        clientService.addClient(client);
        return jsonHandler.successJson();
    }
}
