package carshop_service.exception;

public class NoSuchClientException extends Exception{
    public NoSuchClientException(){
        super("Данный клиент не существует");
    }
}
