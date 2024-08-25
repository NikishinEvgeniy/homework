package carshop.service.exception;

public class ClientIsExistException extends Exception{
    public ClientIsExistException(){
        super("Данный клиент уже существует");
    }
}
