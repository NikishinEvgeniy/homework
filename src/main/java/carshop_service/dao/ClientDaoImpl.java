package carshop_service.dao;

import carshop_service.constant.UserState;
import carshop_service.entity.Client;

import java.util.HashMap;
import java.util.List;

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
        clients.put(client.getId(),client);
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
        this.clients.put(client.getId(),client);
    }

    @Override
    public boolean isExist(Client client) {
        return clients.containsValue(client);
    }
}
