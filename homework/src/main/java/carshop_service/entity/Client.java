package carshop_service.entity;

import java.util.Objects;

public class Client {
    private static int globalId;
    private int id;
    private int countOfBuy;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String role;
    private String state;

    public Client(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getCountOfBuy() {
        return countOfBuy;
    }

    public void setCountOfBuy(int countOfBuy) {
        this.countOfBuy = countOfBuy;
    }

    public Client(String name, String surname, String login, String password, String role, String state) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
        this.id = globalId++;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Client(String state) {
        this.id = globalId++;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(login, client.login) && Objects.equals(password, client.password) && Objects.equals(role, client.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role);
    }

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Имя = " + name + "\n" +
                " Фамилия = " + surname + "\n" +
                " Логин = " + login + "\n" +
                " Пароль = " + password + "\n" +
                " Роль = " + role + "\n" +
                " Состояние = " + state + "\n" +
                " Количество покупок = " + countOfBuy + "\n" +
                "----------------------------\n";
    }
}
