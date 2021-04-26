package abaca.com.voucher.exception;

import abaca.com.voucher.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResultDTO> handleRestException(Exception ex, WebRequest request) {
        String language = request.getHeader("Accept-Language");
        if (!StringUtils.hasLength(language)) {
            language = "en";
        }
        Locale locale = new Locale(language);
        String msg = messageSource.getMessage("msg.error.server", null, locale);
        return ResponseEntity.ok(ResultDTO.builder()
                .success(false)
                .data(null)
                .message(msg)
                .build());
    }
}
