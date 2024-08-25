package carshop.service.entity;

import carshop.service.constant.ClientRole;
import carshop.service.constant.ClientState;
import carshop.service.exception.IncorrectRoleException;
import carshop.service.exception.IncorrectStateException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Сущность client
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private int id;
    private int countOfBuy;
    private String name;
    private String surname;
    private String login;
    private String password;
    private ClientRole role;
    private ClientState state;

    @JsonCreator
    public Client(
            @JsonProperty(value = "countOfBuy", required = true) int countOfBuy,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "surname", required = true) String surname,
            @JsonProperty(value = "login", required = true) String login,
            @JsonProperty(value = "password", required = true) String password,
            @JsonProperty(value = "role", required = true) ClientRole role,
            @JsonProperty(value = "state", required = true) ClientState state) {
        this.countOfBuy = countOfBuy;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
        this.state = state;
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
