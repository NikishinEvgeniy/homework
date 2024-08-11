package carshop_service.entity;

public interface ClientBuilder {
    ClientBuilder id(int id);
    ClientBuilder name(String name);
    ClientBuilder surname(String surname);
    ClientBuilder login(String login);
    ClientBuilder password(String password);
    ClientBuilder role(String role);
    ClientBuilder state(String state);
    ClientBuilder countOfBuy(int countOfBuy);
    Client build();
}
