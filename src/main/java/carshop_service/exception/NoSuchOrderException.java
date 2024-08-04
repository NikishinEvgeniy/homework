package carshop_service.exception;

public class NoSuchOrderException extends RuntimeException{
    public NoSuchOrderException(){
        super("Данный заказ не существует");
    }
}
