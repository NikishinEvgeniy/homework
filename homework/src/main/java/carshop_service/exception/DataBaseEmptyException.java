package carshop_service.exception;

public class DataBaseEmptyException extends Exception{
    public DataBaseEmptyException(String nameOfEntity){
        super("База данных " + nameOfEntity + " не содержит значений");
    }
}
