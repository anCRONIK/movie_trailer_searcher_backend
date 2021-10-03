package site.ancronik.movie.trailer.searcher.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class ImdbApiConfig {

    @Value("${app.imdb.url}")
    private String baseUrl;

    @Value("${app.imdb.host}")
    private String hostHeader;

    @Value("${app.imdb.key}")
    private String key;

    @Value("${app.imdb.webClient-response-timeout-in-seconds}")
    private int responseTimeout;

    @Bean("imdbWebClient")
    public WebClient imdbWebClient() {
        return WebClient.builder().baseUrl(baseUrl).defaultHeader("x-rapidapi-host", hostHeader).defaultHeader("x-rapidapi-key", key)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(responseTimeout))))
            .build();
    }

}
