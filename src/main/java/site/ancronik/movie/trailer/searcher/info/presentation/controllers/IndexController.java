package site.ancronik.movie.trailer.searcher.info.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping({ "", "/" })
public class IndexController {

    private final MessageSource messageSource;

    @Autowired
    public IndexController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS })
    public String getIndexText() {
        return messageSource.getMessage("index.text", null, Locale.getDefault());
    }

}
