package site.ancronik.movie.trailer.searcher.api.data.drivers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieIdTitleData;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieTitleSearchResponse;
import site.ancronik.movie.trailer.searcher.core.util.WebClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ImdbApiCaller {

    static private final String SEARCH_MOVIE_BY_TITLE_PATH = "/movie/imdb_id/byTitle/";

    static private final String GET_MOVIE_BY_ID_PATH = "/movie/id/";

    private final WebClient webClient;

    public ImdbApiCaller(@Qualifier("imdbWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ImdbMovieResponse> searchMoviesByTitle(String title) {
        log.info("Searching movies by title {}", title);

        if (!StringUtils.hasText(title)) {
            return new ArrayList<>();
        }

        return findAllMovieIdsForTitle(title).stream().map(movieTitle -> {
                try {
                    return fetchMovieById(movieTitle.getImdbId());
                } catch (Exception e) {
                    log.error("Error while fetching trailer for {}", movieTitle.getImdbId());
                    log.debug("", e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public ImdbMovieResponse fetchMovieById(String id) {
        log.info("Fetching movie with id {}", id);

        if(!StringUtils.hasText(id)){
            return null;
        }

        return webClient.get().uri(builder -> builder.path(GET_MOVIE_BY_ID_PATH + id + "/")
                .build())
            .retrieve()
            .onStatus(status -> !status.is2xxSuccessful(), WebClientUtil.throwDataServiceException(HttpMethod.GET, GET_MOVIE_BY_ID_PATH, id))
            .bodyToMono(ImdbMovieResponse.class)
            .block();
    }

    public List<ImdbMovieIdTitleData> findAllMovieIdsForTitle(String title) {
        log.debug("Searching for movie id");

        if (!StringUtils.hasText(title)) {
            log.warn("Given title is null!");
            return new ArrayList<>();
        }

        return Objects.requireNonNull(webClient.get().uri(builder -> builder.path(SEARCH_MOVIE_BY_TITLE_PATH + title + "/")
                .build())
            .retrieve()
            .onStatus(status -> !status.is2xxSuccessful(), WebClientUtil.throwDataServiceException(HttpMethod.GET, SEARCH_MOVIE_BY_TITLE_PATH, title))
            .bodyToMono(ImdbMovieTitleSearchResponse.class)
            .block(), "ImdbMovieTitleSearchResponse is null").getData();
    }

}
