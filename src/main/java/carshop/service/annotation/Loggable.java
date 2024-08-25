package carshop.service.annotation;

import carshop.service.constant.LogAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, служащая за логгирование ( считает время выполнения метода, а так же заносит лог в базу данных )
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Loggable {
    LogAction action();
}
