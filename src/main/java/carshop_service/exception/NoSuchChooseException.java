package carshop_service.exception;

public class NoSuchChooseException extends RuntimeException{
    public NoSuchChooseException(){
        super("Данного пункта в меню не существует");
    }
}
