package carshop.service.handler;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * JsonHandler - интерфейс, нужный для граммотного DI
 */
public interface JsonHandler {
    ResponseEntity<Map<String,String>> successJson();
}
