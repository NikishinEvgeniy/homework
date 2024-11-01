package carshop.service.aop;

import carshop.service.entity.Log;
import carshop.service.annotation.Loggable;
import carshop.service.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Аспект, который использует аннотацию Loggable, срабатывает только на том методе, где установлена
 * аннотация @Loggable, расчитывает время выполнения метода, а так же заносит лог (действие,которое сделал пользователь) в базу данных.
 */
@Aspect
@Component
public class LoggingAspect {

    private final LogService logService;

    @Autowired
    public LoggingAspect(LogService logService){
        this.logService = logService;
    }

    @Pointcut("@annotation(com.example.annotation.Loggable)")
    public void logging(){}


    @Around("logging()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (!(proceedingJoinPoint instanceof MethodInvocationProceedingJoinPoint)) {
            return proceedingJoinPoint.proceed();
        }

        MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis() - start;

        Loggable loggableAnnotation = method.getAnnotation(Loggable.class);
        if (loggableAnnotation != null) {
            Log log = Log.builder()
                    .action(loggableAnnotation.action())
                    .dateTime(LocalDateTime.now())
                    .executionDuration(end)
                    .build();
            logService.addLog(log);
        }
        return proceed;
    }
}
