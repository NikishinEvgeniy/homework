package carshop_service.exception;

public class NoSuchClientException extends RuntimeException{
    public NoSuchClientException(){
        super("Данный клиент не существует");
    }
}
