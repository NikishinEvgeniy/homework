package carshop.service.exception;

public class IncorrectActionException extends Exception {
    public IncorrectActionException(){
        super("Указано неверное действие");
    }

}
