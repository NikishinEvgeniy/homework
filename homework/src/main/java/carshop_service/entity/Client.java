package carshop_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
public class Client {

    private int id;
    private int countOfBuy;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String role;
    private String state;

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
