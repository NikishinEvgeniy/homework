package carshop_service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class StandartJasonHandler implements JasonHandler {

    private final ObjectMapper objectMapper;

    public void sendTheJson(HttpServletResponse resp, byte[] jsonBody) throws IOException {
        resp.setContentType("application/json");
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        servletOutputStream.write(jsonBody);
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    public Map<String, String> createSuccessJson() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message", "success");
        return objectObjectHashMap;
    }

    public Map<String, String> createErrorJson(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("error", message);
        return map;
    }

    public String getJsonBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = request.getReader();
        while (reader.ready()) {
            stringBuilder.append(reader.readLine());
        }
        reader.close();
        return stringBuilder.toString();
    }
}
