package site.ancronik.movie.trailer.searcher.info.presentation.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Value("${app.version:UNKNOWN}")
    private String version;

    @GetMapping
    public String getVersion() {
        return version;
    }

}
