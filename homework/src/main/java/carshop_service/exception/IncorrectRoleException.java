package carshop_service.exception;

public class IncorrectRoleException extends Exception{
    public IncorrectRoleException(){
        super("Указана неверная роль");
    }
}
