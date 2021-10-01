package site.ancronik.movie.trailer.searcher.api.domain.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import site.ancronik.movie.trailer.searcher.api.domain.entities.MovieTrailerSearchResponse;

import java.util.List;

@Service
public class MovieTrailerSearchServiceImpl implements MovieTrailerSearchService {

    @Override
    public List<MovieTrailerSearchResponse> searchMovieTrailersByName(@NonNull String name) {
        throw new UnsupportedOperationException("2");
    }

}
