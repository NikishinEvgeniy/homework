package carshop_service.exception;

public class NoSuchCarException extends RuntimeException{
    public NoSuchCarException(){
        super("Данная машина не существует");
    }
}
