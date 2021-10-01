package site.ancronik.movie.trailer.searcher.api.domain.repositories;

import lombok.NonNull;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;

import java.util.List;

public interface MovieTrailerSearchRepository {

    List<MovieTrailerSearchResponse> findAllMovieTrailersForName(@NonNull String name);

}
