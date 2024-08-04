package carshop_service.exception;

public class ClientIsExistException extends RuntimeException{
    public ClientIsExistException(){
        super("Данный клиент уже существует");
    }
}
