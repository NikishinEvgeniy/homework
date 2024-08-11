package carshop_service.exception;

public class NoSuchOrderException extends Exception{
    public NoSuchOrderException(){
        super("Данный заказ не существует");
    }
}
