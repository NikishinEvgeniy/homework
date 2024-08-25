package carshop.service.dao;

import carshop.service.application.DataBaseConfiguration;
import carshop.service.constant.SqlQuery;
import carshop.service.entity.Client;
import carshop.service.annotation.Loggable;
import carshop.service.constant.ClientRole;
import carshop.service.constant.ClientState;
import carshop.service.constant.LogAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных client
 */

@Repository
public class ClientDaoImpl implements ClientDao {

    private final DataBaseConfiguration database;

    @Autowired
    public ClientDaoImpl(DataBaseConfiguration database) {
        this.database = database;
    }

    @Override
    @Loggable(action = LogAction.ADD_CLIENT_ACTION)
    public void addClient(Client client){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.ADD_CLIENT_QUERY);
            statement.setString(1,client.getName());
            statement.setString(2,client.getSurname());
            statement.setString(3,client.getLogin());
            statement.setString(4,client.getPassword());
            statement.setString(5,client.getRole().toString());
            statement.setString(6,client.getState().toString());
            statement.setInt(7,client.getCountOfBuy());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }


    @Override
    public Client getClient(int id) {
        Client client = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.GET_CLIENT_QUERY);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client = Client.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .countOfBuy(resultSet.getInt("count_of_buy"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .state(ClientState.valueOf(resultSet.getString("state")))
                        .role(ClientRole.valueOf(resultSet.getString("role")))
                        .build();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        ResultSet resultSet = null;
        Connection connection = null;
        List<Client> clients = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.GET_CLIENTS_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                clients.add(
                                Client.builder()
                                .id(resultSet.getInt("id"))
                                .name(resultSet.getString("name"))
                                .surname(resultSet.getString("surname"))
                                .countOfBuy(resultSet.getInt("count_of_buy"))
                                .login(resultSet.getString("login"))
                                .password(resultSet.getString("password"))
                                .state(ClientState.valueOf(resultSet.getString("state")))
                                .role(ClientRole.valueOf(resultSet.getString("role")))
                                .build()
                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }

        return clients;
    }

    @Override
    @Loggable(action = LogAction.UPDATE_CLIENT_ACTION)
    public void updateClient(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.UPDATE_CLIENT_QUERY);
            statement.setString(1,client.getName());
            statement.setString(2,client.getSurname());
            statement.setString(3,client.getLogin());
            statement.setString(4,client.getPassword());
            statement.setString(5,client.getRole().toString());
            statement.setString(6,client.getState().toString());
            statement.setInt(7,client.getCountOfBuy());
            statement.setInt(8,client.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, addClient");
            }
        }
    }

    @Override
    public boolean isExist(Client client) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isExist = false;
        try {
            connection = database.getConnection();
            database.setSchema(connection.createStatement());
            statement = connection.prepareStatement(SqlQuery.CLIENT_IS_EXIST_BY_PASSWORD_LOGIN_ROLE_QUERY);
            statement.setString(1,client.getLogin());
            statement.setString(2,client.getPassword());
            statement.setString(3,client.getRole().toString());
            resultSet = statement.executeQuery();
            isExist = resultSet.next();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage() + "Клиент дао, getClient");
            }
        }
        return isExist;
    }
}