package carshop_service.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public interface JasonHandler {
    void sendTheJson(HttpServletResponse resp, byte[] jsonBody) throws IOException;
    Map<String,String> createSuccessJson();
    Map<String,String> createErrorJson(String message);
    String getJsonBody(HttpServletRequest request) throws IOException;
}
