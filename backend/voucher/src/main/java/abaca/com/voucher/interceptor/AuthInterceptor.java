package abaca.com.voucher.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    final
    RequestScopedContext context;

    @Autowired
    public AuthInterceptor(RequestScopedContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {

    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String languageHeader = request.getHeader("Accept-Language");
        String tokenHeader = request.getHeader("Accept-Token");
        context.setLanguage(languageHeader);
        context.setToken(tokenHeader);
        return true;
    }

}
