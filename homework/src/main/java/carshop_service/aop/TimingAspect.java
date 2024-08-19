package carshop_service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class TimingAspect {

    @Pointcut("within(@carshow_service.annotation.Timebale *) && execution(* * (..))")
    public void annotatedByTimebale(){}

    @Around("annotatedByTimebale()")
    public Object timing(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        System.out.println("Finish by time: " + end);
        return result;
    }
}
