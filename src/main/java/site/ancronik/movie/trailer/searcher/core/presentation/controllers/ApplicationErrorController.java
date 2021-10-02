package site.ancronik.movie.trailer.searcher.core.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import site.ancronik.movie.trailer.searcher.core.domain.entity.ErrorInfo;
import site.ancronik.movie.trailer.searcher.core.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping({ "${server.error.path:${error.path:/error}}" })
public class ApplicationErrorController extends AbstractErrorController {

    private final MessageSource messageSource;

    @Autowired
    public ApplicationErrorController(ErrorAttributes errorAttributes, MessageSource messageSource) {
        super(errorAttributes);
        this.messageSource = messageSource;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorInfo> returnJsonDefaultError(HttpServletRequest request) {

        return ResponseEntity.badRequest().body(new ErrorInfo(request.getRequestURI(), LocalDateTime.now(), MessageUtil.getResourceMessage("general.error.msg", messageSource)));
    }

}
