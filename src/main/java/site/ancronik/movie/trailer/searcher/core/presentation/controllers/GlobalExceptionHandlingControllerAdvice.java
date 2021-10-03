package site.ancronik.movie.trailer.searcher.core.presentation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlingControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandlingControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //TODO configure exception handlers if needed

/*    @ExceptionHandler({ AsyncRequestTimeoutException.class })
    public String asyncRequestTimeout(Exception exception) {
        return "";
    }*/

}