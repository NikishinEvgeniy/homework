package carshop.service.configuration;

import carshop.service.handler.JsonHandler;
import carshop.service.handler.JsonHandlerImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


@TestConfiguration
public class ApplicationWebTestConfiguration {

    @Bean
    public JsonHandler jsonHandler(){
        return new JsonHandlerImpl();
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
