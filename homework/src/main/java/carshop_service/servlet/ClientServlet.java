package carshop_service.servlet;

import carshop_service.annotation.Loggable;
import carshop_service.annotation.Timebale;
import carshop_service.application.ConfigLoader;
import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.JsonMessage;
import carshop_service.constant.LogAction;
import carshop_service.dao.ClientDaoImpl;
import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.handler.JasonHandler;
import carshop_service.handler.StandartJasonHandler;
import carshop_service.service.ClientService;
import carshop_service.service.ClientServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@Timebale
@WebServlet("/clients")
@AllArgsConstructor
public class ClientServlet extends HttpServlet {

    private final ObjectMapper objectMapper;
    private final ClientService clientService;
    private final JasonHandler jasonHandler;

    public ClientServlet() {
        ConfigLoader configLoader = new ConfigLoader("application.properties");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
        this.jasonHandler = new StandartJasonHandler(objectMapper);
        this.clientService = new ClientServiceImpl(
                new ClientDaoImpl(
                        new DataBaseConfiguration(
                                configLoader.getProperty("car_service.url"),
                                configLoader.getProperty("car_service.username"),
                                configLoader.getProperty("car_service.password"))
                ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] jsonBody;
        try {
            List<ClientDto> allClients = clientService.getAllClients();
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBody = objectMapper.writeValueAsBytes(allClients);
        } catch (DataBaseEmptyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBody = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        }
        jasonHandler.sendTheJson(resp, jsonBody);
    }


    @Override
    @Loggable(action = LogAction.UPDATE_CLIENT_ACTION)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        Client client = objectMapper.readValue(jsonBodyForUser, Client.class);
        byte[] jsonBodyForServer;
        try {
            clientService.updateClient(client);
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (NoSuchEntityException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }

    @Override
    @Loggable(action = LogAction.ADD_CLIENT_ACTION)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        byte[] jsonBodyForServer;
        try {
            Client client = objectMapper.readValue(jsonBodyForUser, Client.class);
            clientService.addClient(client);
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (DuplicateEntityException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(e.getMessage()));
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }

}
