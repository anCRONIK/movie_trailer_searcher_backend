package site.ancronik.movie.trailer.searcher.api.data.drivers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieTitleSearchResponse;
import site.ancronik.movie.trailer.searcher.core.util.WebClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ImdbApiCaller {

    @Value("${app.imdb.url}")
    private String baseUrl;

    @Value("${app.imdb.host}")
    private String hostHeader;

    @Value("${app.imdb.key}")
    private String key;

    static private final String SEARCH_MOVIE_BY_TITLE_PATH = "/movie/imdb_id/byTitle/";

    static private final String GET_MOVIE_BY_ID_PATH = "/movie/id/";

    private final WebClient webClient;

    public ImdbApiCaller() {
        this.webClient =
            WebClient.builder().baseUrl(baseUrl).defaultHeader("x-rapidapi-host", hostHeader).defaultHeader("x-rapidapi-key", key).defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<ImdbMovieResponse> searchMoviesByTitle(String title) {
        log.info("Searching movies by title {}", title);
        if (!StringUtils.hasText(title)) {
            return new ArrayList<>();
        }

        return findAllMovieIdsForTitle(title).stream().map(movieTitle -> getMovieById(movieTitle.getImdbId())).collect(Collectors.toList());
    }

    public ImdbMovieResponse getMovieById(String id) {
        log.info("Fetching movie with id {}", id);
        return webClient.get().uri(builder -> builder.path(GET_MOVIE_BY_ID_PATH)
                .path(id)
                .build())
            .retrieve()
            .onStatus(status -> !status.is2xxSuccessful(), WebClientUtil.throwDataServiceException(HttpMethod.GET, GET_MOVIE_BY_ID_PATH, id))
            .bodyToMono(ImdbMovieResponse.class)
            .block();
    }

    private List<ImdbMovieTitleSearchResponse> findAllMovieIdsForTitle(String title) {
        log.debug("Searching for movie id");
        return webClient.get().uri(builder -> builder.path(SEARCH_MOVIE_BY_TITLE_PATH)
                .path(title)
                .build())
            .retrieve()
            .onStatus(status -> !status.is2xxSuccessful(), WebClientUtil.throwDataServiceException(HttpMethod.GET, SEARCH_MOVIE_BY_TITLE_PATH, title))
            .bodyToMono(new ParameterizedTypeReference<List<ImdbMovieTitleSearchResponse>>() {
            })
            .block();
    }

}
