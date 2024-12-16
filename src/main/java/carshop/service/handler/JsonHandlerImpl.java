package carshop.service.handler;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик джейсона, нужен для получения джейсона, в котором содержится
 * успешное выполнение метода
 */
@Component
public class JsonHandlerImpl implements JsonHandler {
    @Override
    public ResponseEntity<Map<String, String>> successJson() {
        Map<String,String> successMessage = new HashMap<>();
        successMessage.put("message","success");
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(successMessage);
    }
}
