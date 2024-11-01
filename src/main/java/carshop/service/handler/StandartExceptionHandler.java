package carshop.service.handler;

import carshop.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Вспомогательный контроллер, который обрабатывает все возможные исключения,
 * возникающие в коде. В ответ на исключения он выкидывает JSON
 */

@ControllerAdvice
public class StandartExceptionHandler {

    private ResponseEntity<Map<String,String>> getExceptionMessage(String exceptionMessage){
        Map<String,String> map = new HashMap<>();
        map.put("message",exceptionMessage);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(Exception exception){
        return getExceptionMessage(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(NoSuchEntityException noSuchEntityException){
        return getExceptionMessage(noSuchEntityException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(DataBaseEmptyException dataBaseEmptyException){
        return getExceptionMessage(dataBaseEmptyException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(ClientIsExistException clientIsExistException){
        return getExceptionMessage(clientIsExistException.getMessage());

    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(DuplicateEntityException duplicateEntityException){
        return getExceptionMessage(duplicateEntityException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(IncorrectActionException incorrectActionException){
        return getExceptionMessage(incorrectActionException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(IncorrectRoleException incorrectRoleException){
        return getExceptionMessage(incorrectRoleException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(IncorrectStateException incorrectStateException){
        return getExceptionMessage(incorrectStateException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(IncorrectTypeException incorrectTypeException){
        return getExceptionMessage(incorrectTypeException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(NoSuchChooseException noSuchChooseException){
        return getExceptionMessage(noSuchChooseException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleException(IOException ioException){
        return getExceptionMessage(ioException.getMessage());
    }

}
