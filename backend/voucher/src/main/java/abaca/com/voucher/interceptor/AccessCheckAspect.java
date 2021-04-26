package abaca.com.voucher.interceptor;

import abaca.com.voucher.annotation.AccessCheck;
import abaca.com.voucher.service.AuthService;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AccessCheckAspect {
    protected final RequestScopedContext context;

    final
    AuthService authService;

    @Autowired
    public AccessCheckAspect(RequestScopedContext context, AuthService authService) {
        this.context = context;
        this.authService = authService;
    }

    @Before("@annotation(accessCheck)")
    public void accessCheck(AccessCheck accessCheck) throws Exception {

        boolean accessResult = authService.isHadPermission(context.getToken(),
                 accessCheck.accessType());
        log.info("Check token is " + accessCheck);
        if (!accessResult) {
            throw new Exception("Not allow");
        }
    }

}
