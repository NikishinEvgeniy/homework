package carshop_service.exception;

public class NoSuchCarException extends Exception{
    public NoSuchCarException(){
        super("Данная машина не существует");
    }
}
