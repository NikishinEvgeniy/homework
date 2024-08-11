package carshop_service.application;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConfigLoader {
    private final String url;
    private final String username;
    private final String password;

    private Properties properties = new Properties();

    public ConfigLoader(String configFileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти конфигурационный файл: " + configFileName);
            }
            properties.load(input);
            this.url = getProperty("car_service.url");
            this.password = getProperty("car_service.password");
            this.username = getProperty("car_service.username");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки конфигурационного файла: " + configFileName, e);
        }
    }
    public ConfigLoader(String url, String password, String username) {
            this.url = url;
            this.password = password;
            this.username = username;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении соединения с базой данных " + e.getMessage());
        }
        return connection;
    }
    public void setSchema(Statement statement){
        try {
            statement.execute("SET search_path TO private_schema;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}