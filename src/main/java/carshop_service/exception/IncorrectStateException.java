package carshop_service.exception;

public class IncorrectStateException extends RuntimeException{
    public IncorrectStateException(){
        super("Статус не существует");
    }
}
