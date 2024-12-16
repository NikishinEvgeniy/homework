package carshop.service.exception;

public class DuplicateEntityException extends Exception{
    public DuplicateEntityException(String nameOfEntity){
        super("Сущность " + nameOfEntity + " уже существует в базе данных ");
    }
}
