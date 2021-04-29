package abaca.com.prepaid.data.exception;

import abaca.com.prepaid.data.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResultDTO> handleRestException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
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
