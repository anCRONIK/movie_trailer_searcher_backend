package site.ancronik.movie.trailer.searcher.info.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public String index() {
        return messageSource.getMessage("index.text", null, Locale.getDefault());
    }

}
