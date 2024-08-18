package carshop_service.dao;

import carshop_service.constant.UserState;
import carshop_service.entity.Client;
<<<<<<< Updated upstream

import java.util.HashMap;
import java.util.List;

=======
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Имплементация интерфейса, DAO - data accept object, класс общающийся с базой данных client
 */
@AllArgsConstructor
>>>>>>> Stashed changes
public class ClientDaoImpl implements ClientDao {

    private HashMap<Integer, Client> clients;
    public ClientDaoImpl(){
        this.clients = new HashMap<>();
        Client client1 = new Client("Zhenya","Nikishin","login","password","admin", UserState.BEGIN_STATE);
        Client client2 = new Client("Arsen","Arsenovich","123","123","client", UserState.BEGIN_STATE);
        Client client3 = new Client("Sasha","Zumbickov","login22","password33","client", UserState.BEGIN_STATE);
        Client client4 = new Client("Zaur","Tregulov","qwerty","qwerty","manager", UserState.BEGIN_STATE);
        clients.put(client1.getId(), client1);
        clients.put(client2.getId(), client2);
        clients.put(client3.getId(), client3);
        clients.put(client4.getId(), client4);
    }


    @Override
    public void addClient(Client client){
<<<<<<< Updated upstream
        clients.put(client.getId(),client);
=======
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
            if(client.getRole() != null) statement.setString(5,client.getRole().toString());
            else statement.setString(5,null);
            if(client.getState() != null) statement.setString(6,client.getState().toString());
            else statement.setString(6,null);
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
>>>>>>> Stashed changes
    }

    @Override
    public Client getClient(int id) {
        return this.clients.get(id);
    }

    @Override
    public List<Client> getAllClients() {
        return this.clients.values().stream().toList();
    }

    @Override
    public void updateClient(Client client) {
<<<<<<< Updated upstream
        this.clients.put(client.getId(),client);
=======
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
            if(client.getRole() != null) statement.setString(5,client.getRole().toString());
            else statement.setString(5,null);
            if(client.getState() != null) statement.setString(6,client.getState().toString());
            else statement.setString(6,null);
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
>>>>>>> Stashed changes
    }

    @Override
    public boolean isExist(Client client) {
        return clients.containsValue(client);
    }
}
