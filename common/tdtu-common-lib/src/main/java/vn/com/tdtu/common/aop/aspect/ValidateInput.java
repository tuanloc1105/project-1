package vn.com.tdtu.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import vn.com.tdtu.common.aop.Validate;
import vn.com.tdtu.common.log.LOG;

@Aspect
@Component
public class ValidateInput {

    @Around("execution(public * vn.com..*.controller.*.*(..))")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Validate.validateInput(joinPoint);
        } catch (Throwable e) {
            LOG.exception(e);
            throw e;
        }
        return joinPoint.proceed();
    }

}
