package carshop.service.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс, нужны для того, чтобы считывать значение из файла, в данном случае yml формата
 */
public class ConfigLoader {
    private Properties properties = new Properties();

    public ConfigLoader(String configFileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти конфигурационный файл: " + configFileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки конфигурационного файла: " + configFileName, e);
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
