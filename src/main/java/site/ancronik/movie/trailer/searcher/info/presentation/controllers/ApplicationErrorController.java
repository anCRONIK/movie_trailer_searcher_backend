package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import site.ancronik.movie.trailer.searcher.core.domain.entity.ErrorInfo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequestMapping({ "${server.error.path:${error.path:/error}}" })
public class ApplicationErrorController implements ErrorController {

    private final MessageSource messageSource;

    @Autowired
    public ApplicationErrorController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorInfo> returnJsonDefaultError(HttpServletRequest request) {

        return ResponseEntity.badRequest().body(new ErrorInfo(request.getRequestURI(), LocalDateTime.now(), messageSource.getMessage("general.error.msg", null, Locale.getDefault())));
    }

}
