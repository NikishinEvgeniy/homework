package carshop.service.exception;

public class NoSuchEntityException extends Exception{
    public NoSuchEntityException(String nameOfEntity){
        super("Сущность: " + nameOfEntity + " не найдена ");
    }
}
