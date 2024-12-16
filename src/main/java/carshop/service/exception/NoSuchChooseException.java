package carshop.service.exception;

public class NoSuchChooseException extends Exception{
    public NoSuchChooseException(){
        super("Данного пункта в меню не существует");
    }
}
