package site.ancronik.movie.trailer.searcher.api.data.repositories;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import site.ancronik.movie.trailer.searcher.api.data.drivers.ImdbApiCaller;
import site.ancronik.movie.trailer.searcher.api.data.models.ImdbMovieResponse;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;
import site.ancronik.movie.trailer.searcher.core.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository("imdbSearchRepository")
@Slf4j
public class ImdbSearchRepository implements MovieTrailerSearchRepository {

    private final ImdbApiCaller apiCaller;
    private final Mapper<ImdbMovieResponse, MovieTrailerSearchResponse> imdbMovieResponseMovieTrailerSearchResponseMapper;
    private final UrlValidator urlValidator;

    @Autowired
    public ImdbSearchRepository(ImdbApiCaller apiCaller,
        Mapper<ImdbMovieResponse, MovieTrailerSearchResponse> imdbMovieResponseMovieTrailerSearchResponseMapper, UrlValidator urlValidator) {
        this.apiCaller = apiCaller;
        this.imdbMovieResponseMovieTrailerSearchResponseMapper = imdbMovieResponseMovieTrailerSearchResponseMapper;
        this.urlValidator = urlValidator;
    }

    @Override
    @Cacheable(value = "movieTrailersSearchCache", key = "#request.searchTitle")
    public List<MovieTrailerSearchResponse> findAllMovieTrailersForName(@NonNull MovieTrailerSearchRequest request) {

        return imdbMovieResponseMovieTrailerSearchResponseMapper.mapToList(apiCaller.searchMoviesByTitle(request.getSearchTitle()))
            .stream().filter(data -> !data.getTrailers().isEmpty() && urlValidator.isValid(data.getTrailers().get(0))).collect(Collectors.toList());
    }

}
