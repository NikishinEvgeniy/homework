package carshop_service.entity;

public class StandartClientBuilder implements ClientBuilder {

    private int id;
    private int countOfBuy;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String role;
    private String state;

    @Override
    public ClientBuilder id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ClientBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ClientBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public ClientBuilder login(String login) {
        this.login = login;
        return this;
    }

    @Override
    public ClientBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public ClientBuilder role(String role) {
        this.role = role;
        return this;
    }

    @Override
    public ClientBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public ClientBuilder countOfBuy(int countOfBuy) {
        this.countOfBuy = countOfBuy;
        return this;
    }

    @Override
    public Client build() {
        return new Client(this.id,this.countOfBuy,this.name,this.surname,this.login,this.password,this.role,this.state);
    }
}
