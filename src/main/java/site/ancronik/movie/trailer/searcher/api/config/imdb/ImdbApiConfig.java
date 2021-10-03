package site.ancronik.movie.trailer.searcher.api.config.imdb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

    @Bean
    @ConfigurationProperties(prefix = "app.imdb")
    public ImdbApiConfigProperties imdbApiConfigProperties() {
        return new ImdbApiConfigProperties();
    }

    @Value("${app.webClient-response-timeout-in-seconds}")
    private int responseTimeout;

    @Bean("imdbWebClient")
    public WebClient imdbWebClient(ImdbApiConfigProperties configProperties) {
        return WebClient.builder().baseUrl(configProperties.getUrl()).defaultHeader("x-rapidapi-host", configProperties.getHost()).defaultHeader("x-rapidapi-key", configProperties.getKey())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(responseTimeout))))
            .build();
    }

}
