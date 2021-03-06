package site.ancronik.movie.trailer.searcher.api.domain.services;

import lombok.NonNull;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchRequest;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;

import java.util.List;

public interface MovieTrailerSearchService {

    List<MovieTrailerSearchResponse> searchMovieTrailersForTitle(@NonNull MovieTrailerSearchRequest request);

}
