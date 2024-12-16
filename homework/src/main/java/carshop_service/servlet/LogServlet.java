package carshop_service.servlet;

import carshop_service.annotation.Timebale;
import carshop_service.application.ConfigLoader;
import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.JsonMessage;
import carshop_service.dao.LogDaoImpl;
import carshop_service.dto.LogDto;
import carshop_service.entity.Log;
import carshop_service.handler.JasonHandler;
import carshop_service.handler.StandartJasonHandler;
import carshop_service.service.LogService;
import carshop_service.service.LogServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@Timebale
@WebServlet("/logs")
@AllArgsConstructor
public class LogServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final LogService logService;
    private final JasonHandler jasonHandler;

    public LogServlet() {
        ConfigLoader configLoader = new ConfigLoader("application.properties");
        this.objectMapper = new ObjectMapper();
        this.jasonHandler = new StandartJasonHandler(objectMapper);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, true);
        this.logService = new LogServiceImpl(
                new LogDaoImpl(
                        new DataBaseConfiguration(
                                configLoader.getProperty("car_service.url"),
                                configLoader.getProperty("car_service.username"),
                                configLoader.getProperty("car_service.password"))
                ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] jsonBody;
        List<LogDto> logDtos = logService.getAllLogs();
        resp.setStatus(HttpServletResponse.SC_OK);
        jsonBody = objectMapper.writeValueAsBytes(logDtos);
        jasonHandler.sendTheJson(resp, jsonBody);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBodyForUser = jasonHandler.getJsonBody(req);
        byte[] jsonBodyForServer;
        try {
            Log log = objectMapper.readValue(jsonBodyForUser, Log.class);
            logService.addLog(log);
            resp.setStatus(HttpServletResponse.SC_OK);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            jsonBodyForServer = objectMapper.writeValueAsBytes(jasonHandler.createErrorJson(JsonMessage.JSON_WRONG_FORMAT));
        }
        jasonHandler.sendTheJson(resp, jsonBodyForServer);
    }
}
