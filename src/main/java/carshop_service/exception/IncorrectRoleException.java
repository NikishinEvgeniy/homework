package carshop_service.exception;

public class IncorrectRoleException extends RuntimeException{
    public IncorrectRoleException(){
        super("Роль не существует");
    }
}
