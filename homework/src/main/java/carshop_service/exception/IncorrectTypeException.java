package carshop_service.exception;

public class IncorrectTypeException extends Exception{
    public IncorrectTypeException(){
        super("Указан неверный тип заказа");
    }
}
