package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.ancronik.movie.trailer.searcher.core.util.MessageUtil;
import site.ancronik.movie.trailer.searcher.info.domain.entities.InfoResponse;

import java.util.Locale;

@RestController
@RequestMapping({ "", "/" })
public class InfoController {

    private final MessageSource messageSource;

    @Value("${info.app.version:UNKNOWN}")
    private String version;

    @Autowired
    public InfoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS })
    public InfoResponse getInfo() {
        return new InfoResponse(MessageUtil.getResourceMessage("info.message", messageSource), version);
    }

}
