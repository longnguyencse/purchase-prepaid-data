package abaca.com.voucher.interceptor;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestScopedContext {
    private String token;
    private String language;
}
