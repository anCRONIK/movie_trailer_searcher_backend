package site.ancronik.movie.trailer.searcher.api.data.repositories;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;
import site.ancronik.movie.trailer.searcher.api.domain.repositories.MovieTrailerSearchRepository;

import java.util.List;

@Component("imdbSearchRepository")
public class ImdbSearchRepository implements MovieTrailerSearchRepository {

    @Override
    public List<MovieTrailerSearchResponse> findAllMovieTrailersForName(@NonNull String name) {
        throw new UnsupportedOperationException("roki");
    }

}
