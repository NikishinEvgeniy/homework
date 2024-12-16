package carshop_service.servlet;

import carshop_service.annotation.Timebale;
import carshop_service.application.ConfigLoader;
import carshop_service.application.DataBaseConfiguration;
import carshop_service.dao.LogDaoImpl;
import carshop_service.handler.JasonHandler;
import carshop_service.handler.StandartJasonHandler;
import carshop_service.service.LogService;
import carshop_service.service.LogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

@Timebale
@WebServlet("/logs/export")
@AllArgsConstructor
public class ExportLogsServlet extends HttpServlet {
    private final LogService logService;
    private final JasonHandler jasonHandler;
    private final ObjectMapper objectMapper;

    public ExportLogsServlet() {
        this.objectMapper = new ObjectMapper();
        this.jasonHandler = new StandartJasonHandler(objectMapper);
        ConfigLoader configLoader = new ConfigLoader("application.properties");
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
        logService.exportLogsInTextFile();
        resp.setStatus(HttpServletResponse.SC_OK);
        byte[] bytes = objectMapper.writeValueAsBytes(jasonHandler.createSuccessJson());
        jasonHandler.sendTheJson(resp, bytes);
    }
}
