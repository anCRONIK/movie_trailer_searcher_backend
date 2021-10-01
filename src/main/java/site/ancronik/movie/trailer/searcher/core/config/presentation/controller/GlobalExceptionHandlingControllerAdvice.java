package site.ancronik.movie.trailer.searcher.core.config.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlingControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandlingControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Convert a predefined exception to an HTTP Status code and specify the name of a specific view that will be used to display the error.
     *
     * @return Exception view.
     */
    @ExceptionHandler({ SQLException.class }) //FIXME
    public String databaseError(Exception exception) {
        // Nothing to do. Return value 'databaseError' used as logical view name
        // of an error page, passed to view-resolver(s) in usual way.
        log.error("Request raised " + exception.getClass().getSimpleName());
        return "databaseError";
    }

}