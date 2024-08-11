package carshop_service.exception;

public class IncorrectStateException extends Exception{
    public IncorrectStateException(){
        super("Статус не существует");
    }
}
