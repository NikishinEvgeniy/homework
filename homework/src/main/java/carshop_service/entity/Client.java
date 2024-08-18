package carshop_service.entity;

import carshop_service.constant.ClientRole;
import carshop_service.constant.ClientState;
import carshop_service.exception.IncorrectRoleException;
import carshop_service.exception.IncorrectStateException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Сущность client
 */
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
    private ClientRole role;
    private ClientState state;

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

    public static ClientBuilder builder(){
        return new ClientBuilder();
    }

    public static class ClientBuilder{
        private int id;
        private int countOfBuy;
        private String name;
        private String surname;
        private String login;
        private String password;
        private ClientRole role;
        private ClientState state;

        public ClientBuilder id(int id){
            this.id = id;
            return this;
        }
        public ClientBuilder name(String name){
            this.name = name;
            return this;
        }
        public ClientBuilder surname(String surname){
            this.surname = surname;
            return this;
        }
        public ClientBuilder countOfBuy(int countOfBuy){
            this.countOfBuy = countOfBuy;
            return this;
        }
        public ClientBuilder login(String login){
            this.login = login;
            return this;
        }
        public ClientBuilder password(String password){
            this.password = password;
            return this;
        }
        public ClientBuilder role(ClientRole role){
            this.role = role;
            return this;
        }
        public ClientBuilder state(ClientState state){
            this.state = state;
            return this;
        }
        public ClientBuilder checkState() throws IncorrectStateException {
            if(Arrays.stream(ClientState.values()).noneMatch(x -> x.equals(this.state))) throw new IncorrectStateException();
            return this;
        }
        public ClientBuilder checkRole() throws IncorrectRoleException {
            if(Arrays.stream(ClientRole.values()).noneMatch(x -> x.equals(this.role))) throw new IncorrectRoleException();
            return this;
        }

        public Client build(){
            return new Client(this.id,this.countOfBuy,this.name,this.surname,this.login,this.password,this.role,this.state);
        }
    }
}
