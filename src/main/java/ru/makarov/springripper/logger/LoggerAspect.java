package ru.makarov.springripper.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sample of Aspect work for logger.
 * This is not for production. Here research only different functionality and some metrics.
 */
@Profile("aspect")
@Component
@Aspect
public class LoggerAspect {
    private Logger logger = Logger.getLogger(LoggerAspect.class.getName());

    @Pointcut("within(ru.makarov.springripper.service.PersonServis)")
    public void servicePersonLoggerProcessingMethods() {
    }

    @Pointcut("within(ru.makarov.springripper.service.DepartmentService)")
    public void serviceDepartmentLoggerProcessingMethods() {
    }

    @After("servicePersonLoggerProcessingMethods()")
    public void LoggerPerson(JoinPoint joinPoint) {
        String methods = joinPoint.getSignature().getName();
        logger.log(Level.INFO, "PERSON вызов метода: " + methods);
    }

    @After("serviceDepartmentLoggerProcessingMethods()")
    public void LoggerDepartment(JoinPoint joinPoint) {
        String methods = joinPoint.getSignature().getName();
        logger.log(Level.INFO, "DEPARTMENT вызов метода: " + methods);
    }

    @AfterReturning(value = "servicePersonLoggerProcessingMethods()", returning = "result")
    public void logAfterReturningPerson(JoinPoint joinPoint, Object result) {
        if (result != null) {
            invokePersonLog(result);
        } else {
            logger.log(Level.INFO, "PERSON возвращенное значение: " + null);
        }
    }

    private void invokePersonLog(Object result) {
        if (result.getClass().getSimpleName().equals("Person")) {
            logger.log(Level.INFO, "REPORT возвращенное значение: " + invokePersonGetName(result));
        }
        if (result.getClass().getSimpleName().equals("ArrayList")){
            List persons = (List) result;
            StringBuilder value = new StringBuilder();
            for (Object person : persons) {
               value.append(invokePersonGetName(person)).append(" ");
            }
            logger.log(Level.INFO, "REPORT FULL LIST" +  value.toString());
            }
        }


    private String invokePersonGetName(Object result) {
        String value = null;
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("getName")) {
                value = (String) ReflectionUtils.invokeMethod(method, result);
            }
        }
        return value;
    }

    /**
     * The stronges advise
     *
     * @param joinPoint - point of method.
     * @return - result of method.
     * @throws Throwable -exception.
     */
    @Around("servicePersonLoggerProcessingMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.log(Level.INFO, "PERSON method " + joinPoint.getSignature().getName() + " выполнен за " + executionTime + "мс");
        return proceed;
    }
}
