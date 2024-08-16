package carshop_service.exception;

public class IncorrectActionException extends Exception {
    public IncorrectActionException(){
        super("Указано неверное действие");
    }

}
