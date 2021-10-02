package site.ancronik.movie.trailer.searcher.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ImdbApiConfig {

    @Value("${app.imdb.url}")
    private String baseUrl;

    @Value("${app.imdb.host}")
    private String hostHeader;

    @Value("${app.imdb.key}")
    private String key;

    @Bean("imdbWebClient")
    public WebClient imdbWebClient() {
        return WebClient.builder().baseUrl(baseUrl).defaultHeader("x-rapidapi-host", hostHeader).defaultHeader("x-rapidapi-key", key)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

}
