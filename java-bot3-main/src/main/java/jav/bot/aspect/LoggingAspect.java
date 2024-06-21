package jav.bot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* jav.bot.service..*(..)) || execution(* jav.bot.controller..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* jav.bot.service..*(..)) || execution(* jav.bot.controller..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Method executed: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }
}
