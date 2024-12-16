package carshop_service.aop;

import carshop_service.annotation.Loggable;
import carshop_service.application.ConfigLoader;
import carshop_service.application.DataBaseConfiguration;
import carshop_service.constant.LogAction;
import carshop_service.dao.LogDaoImpl;
import carshop_service.entity.Log;
import carshop_service.service.LogService;
import carshop_service.service.LogServiceImpl;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.LocalDateTime;

@Aspect
@AllArgsConstructor
public class LoggingAspect {
    private LogService logService;

    public LoggingAspect(){
        ConfigLoader configLoader = new ConfigLoader("application.properties");
        this.logService = new LogServiceImpl(
                new LogDaoImpl(
                        new DataBaseConfiguration(
                                configLoader.getProperty("car_service.url"),
                                configLoader.getProperty("car_service.username"),
                                configLoader.getProperty("car_service.password"))
                ));
    }

    @Pointcut("within(@carshow_service.annotation.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable(){}

    @After("annotatedByLoggable()")
    public void logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Loggable loggable = methodSignature.getMethod().getAnnotation(Loggable.class);
        LogAction action = loggable.action();

        logService.addLog(Log.builder()
                .action(action)
                .dateTime(LocalDateTime.now())
                .build());
    }
}