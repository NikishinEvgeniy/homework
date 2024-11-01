package carshop.service.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Обычная конфигурация приложения
 */

@Configuration
public class ApplicationConfig {

    @Bean
    public ConfigLoader configLoaderBean(){
        return new ConfigLoader("application.yml");
    }

    @Bean
    public DataBaseConfiguration dataBaseConfigurationBean(){
        ConfigLoader configLoader = configLoaderBean();
        return new DataBaseConfiguration(
                configLoader.getProperty("url"),
                configLoader.getProperty("username"),
                configLoader.getProperty("password")
        );
    }
}
