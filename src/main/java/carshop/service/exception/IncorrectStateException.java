package carshop.service.exception;

public class IncorrectStateException extends Exception{
    public IncorrectStateException(){
        super("Указан неверный статус");
    }
}
