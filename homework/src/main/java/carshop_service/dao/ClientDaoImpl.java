package carshop_service.dao;

import carshop_service.application.ConfigLoader;
import carshop_service.entity.Client;
import carshop_service.entity.StandartClientBuilder;
import lombok.AllArgsConstructor;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class ClientDaoImpl implements ClientDao {

    private ConfigLoader configLoader;

    @Override
    public void addClient(Client client){
        Connection connection = configLoader.getConnection();
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String addClientQuery = "INSERT INTO client (name,surname,login,password,role,state,count_of_buy) " +
                    "VALUES(?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(addClientQuery);
            statement.setString(1,client.getName());
            statement.setString(2,client.getSurname());
            statement.setString(3,client.getLogin());
            statement.setString(4,client.getPassword());
            statement.setString(5,client.getRole());
            statement.setString(6,client.getState());
            statement.setInt(7,client.getCountOfBuy());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }

    @Override
    public Client getClient(int id) {
        Connection connection = configLoader.getConnection();
        Client client = null;
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getClientQuery = "SELECT * FROM client Where id =?";
            statement = connection.prepareStatement(getClientQuery);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            client = new StandartClientBuilder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .countOfBuy(resultSet.getInt("count_of_buy"))
                    .login(resultSet.getString("login"))
                    .password(resultSet.getString("password"))
                    .state(resultSet.getString("state"))
                    .role(resultSet.getString("role"))
                    .build();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }

        return client;
    }

    @Override
    public List<Client> getAllClients() {
        Connection connection = configLoader.getConnection();
        List<Client> clients = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String getClientsQuery = "SELECT * FROM client";
            statement = connection.prepareStatement(getClientsQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                clients.add(
                        new StandartClientBuilder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .surname(resultSet.getString("surname"))
                                .countOfBuy(resultSet.getInt("count_of_buy"))
                                .login(resultSet.getString("login"))
                                .password(resultSet.getString("password"))
                                .state(resultSet.getString("state"))
                                .role(resultSet.getString("role"))
                                .build()
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }

        return clients;
    }

    @Override
    public void updateClient(Client client) {
        Connection connection = configLoader.getConnection();
        PreparedStatement statement = null;
        try {
            configLoader.setSchema(connection.createStatement());
            String addClientQuery = "UPDATE client " +
                    "SET name = ?,surname = ?, " +
                    "login = ?, password = ?, " +
                    "role = ?, state = ?, " +
                    "count_of_buy = ? " +
                    "Where id = ?;";
            statement = connection.prepareStatement(addClientQuery);
            statement.setString(1,client.getName());
            statement.setString(2,client.getSurname());
            statement.setString(3,client.getLogin());
            statement.setString(4,client.getPassword());
            statement.setString(5,client.getRole());
            statement.setString(6,client.getState());
            statement.setInt(7,client.getCountOfBuy());
            statement.setInt(8,client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }

    @Override
    public boolean isExist(Client client) {
        Connection connection = configLoader.getConnection();
        PreparedStatement statement = null;
        boolean isExist = false;
        try {
            configLoader.setSchema(connection.createStatement());
            String getClientQuery = "SELECT * FROM client" +
                    " Where login =? and password =? and role =?";
            statement = connection.prepareStatement(getClientQuery);
            statement.setString(1,client.getLogin());
            statement.setString(2,client.getPassword());
            statement.setString(3,client.getRole());
            ResultSet resultSet = statement.executeQuery();
            isExist = resultSet.next();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return isExist;
    }
}
