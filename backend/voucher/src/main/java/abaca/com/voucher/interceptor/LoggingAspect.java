package abaca.com.voucher.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@within(abaca.com.voucher.annotation.Loggable)")
    public void loggable() {
    }

    @Before("loggable()")
    public void logParams(JoinPoint joinPoint) {
        String className = joinPoint.getSourceLocation().getWithinType().getName();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Logger logger = LoggerFactory.getLogger(className);
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            if (paramName != null && paramName.toLowerCase().contains("pass")) {
                if (args.length > i) {
                    args[i] = "*****";
                }
            }
        }
        log.info("call {} with params: {} values {}", joinPoint.getSignature().toShortString(), parameterNames, args);
    }

    @Around("loggable()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        String result = "";
        if (proceed == null) {
            result = "NULL";
        } else {
            result = proceed.toString();
        }

        log.info("{} executed in {}ms, return = {}", joinPoint.getSignature().toShortString(), executionTime, result);
        return proceed;
    }
}
