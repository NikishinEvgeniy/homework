package carshop_service.exception;

public class DuplicateClientException extends Exception {
    public DuplicateClientException(){
        super("Данный клиент, уже находиться в базе данных");
    }
}
